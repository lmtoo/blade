package cn.accessbright.blade.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.icitic.hrms.common.action.GenericAction;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.core.event.MessageEvent;
import com.icitic.hrms.core.util.ClassUtils;
import com.icitic.hrms.core.util.Validatable;
import com.icitic.hrms.util.Tools;

/**
 * ����������action�� ������ʵ���������actָ���ķ���������������������ѡ�� User.class, ActionMapping.class,
 * HttpServletRequest.class, HttpSession.class, HttpServletResponse.class,
 * ServletContext.class,
 * HashMap.class,��ʾ��ǰ����������ŵ�һ��hashMap�У������һ����ֵ�������value������һ���ַ�������
 * 
 * @author ll
 * 
 */
public abstract class ParamDrivenAction extends GenericAction {
	public static final String REDIRECT_PREFIX = "redirect:";

	private static ThreadLocal localMgr = new ThreadLocal();

	// ������������-->action������������
	private Map methodParamTypeToRequestParamTypeMapping = Collections.synchronizedMap(new HashMap());

	private List delegates = Collections.synchronizedList(new LinkedList());

	public ParamDrivenAction() {
		bindDelegateObjects(delegates);
	}

	public final ActionForward executeDo(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = mapping.findForward("list");
		try {
			beforeAction(user, session, mapping, form, request, response);
			ActionForward theForward = findAndExecuteTargetMethod(user, session, mapping, form, request, response);
			if (theForward != null)
				forward = theForward;
			afterAction(user, session, mapping, form, request, response);
		} catch (HrmsException he) {
			publishEvent(new MessageEvent(he, he.getMessage()));
		} catch (Exception e) {
			ActionError ae = new ActionError("info", "<div style='font:red'>����</div>", e.getMessage(), e.toString()+e.getCause()!=null?e.getCause().toString():"");
			this.actionErrors.add(ae);
		} finally {
			clearLocalManager();
		}
		return forward;
	}

	/**
	 * ��ȡmanager����
	 * 
	 * @return
	 * @throws HrmsException
	 */
	protected Object getLocalManager() throws HrmsException {
		if (localMgr.get() == null) {
			localMgr.set(createLocalManager());
		}
		return localMgr.get();
	}

	/**
	 * ���manager����
	 */
	private void clearLocalManager() {
		localMgr.set(null);
	}

	/**
	 * ����Manager����
	 * 
	 * @return
	 */
	protected abstract Object createLocalManager() throws HrmsException;

	/**
	 * actionִ��֮ǰҪִ�еĴ��룬���������Ҫ��д
	 * 
	 * @param user
	 * @param session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void beforeAction(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws HrmsException {
	}

	/**
	 * action֮��Ҫִ�еĴ��룬���������Ҫ��д
	 * 
	 * @param user
	 * @param session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void afterAction(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws HrmsException {
	}

	/**
	 * ���Ҳ�ִ��Ŀ�귽��
	 * 
	 * @param user
	 * @param session
	 * @param actionMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws HrmsException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private ActionForward findAndExecuteTargetMethod(User user, HttpSession session, ActionMapping actionMapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws HrmsException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String act = request.getParameter("act");
		if (Tools.isEmpty(act))
			throw new HrmsException("act��������Ϊ��", this.getClass());
		Map paramTypeToValueMapping = getParamTypeToValueMapping(user, session, actionMapping, form, request, response);

		Iterator iter = delegates.iterator();
		while (iter.hasNext()) {
			// ���жϴ������Ȼ�����жϱ�action
			ActionForward forward = doExecuteTargetMethod(request, iter.next(), act, paramTypeToValueMapping, actionMapping);
			if (forward != null)
				return forward;
		}
		return doExecuteTargetMethod(request, this, act, paramTypeToValueMapping, actionMapping);
	}

	/**
	 * ִ��Ŀ�귽��
	 * 
	 * @param target
	 * @param methodName
	 * @param paramTypeToValueMapping
	 * @param actionMapping
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private ActionForward doExecuteTargetMethod(HttpServletRequest request, Object target, String methodName, Map paramTypeToValueMapping,
			ActionMapping actionMapping) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (target == null)
			return null;
		List methods = ClassUtils.getMethodsByName(target.getClass(), methodName);
		Object forward = null;
		for (int i = 0; i < methods.size(); i++) {
			Method method = (Method) methods.get(i);
			Object[] args = getMethodBindArgs(method, paramTypeToValueMapping);
			method.setAccessible(true);
			Object retValue = method.invoke(target, args);
			if (retValue != null) {
				forward = retValue;
			}
		}
		return handleReturndAction(request, actionMapping, forward);
	}

	/**
	 * ������ֵΪActionForward����redirect
	 * 
	 * @param request
	 * @param mapping
	 * @param retValue
	 * @return
	 */
	private ActionForward handleReturndAction(HttpServletRequest request, ActionMapping mapping, Object retValue) {
		if (retValue == null)
			return null;
		if (retValue instanceof ActionForward) {
			return (ActionForward) retValue;
		} else {
			ActionForward forward = null;
			String input = (String) retValue;
			if (input.startsWith(REDIRECT_PREFIX)) {
				forward = new ActionForward(request.getContextPath() + input.substring(REDIRECT_PREFIX.length()), true);
			} else {
				forward = mapping.findForward(input);
			}
			return forward;
		}
	}

	/**
	 * �󶨴������ֻ�ܰ���״̬���󣬷�����Ҫ����ά������
	 * 
	 * @param deletes
	 */
	protected void bindDelegateObjects(List delegates) {
	}

	/**
	 * ��ȡ�����������ӳ��
	 * 
	 * @param user
	 * @param session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private Map getParamTypeToValueMapping(User user, HttpSession session, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map paramValueMapping = new HashMap();
		paramValueMapping.put(User.class, user);
		paramValueMapping.put(ActionMapping.class, mapping);
		paramValueMapping.put(HttpServletRequest.class, request);
		paramValueMapping.put(HttpSession.class, request.getSession());
		paramValueMapping.put(HttpServletResponse.class, response);
		paramValueMapping.put(ServletContext.class, request.getSession().getServletContext());
		paramValueMapping.put(ActionForm.class, form);
		paramValueMapping.put(HashMap.class, getParamMap(request));
		return paramValueMapping;
	}

	/**
	 * ��ȡ�󶨵Ĳ�����������
	 * 
	 * @param method
	 * @param paramTypeToValueMapping
	 * @return
	 */
	private Object[] getMethodBindArgs(Method method, Map paramTypeToValueMapping) {
		Class[] methodParamTypes = method.getParameterTypes();
		Object[] args = new Object[methodParamTypes.length];
		for (int i = 0; i < methodParamTypes.length; i++) {
			Class methodParamType = methodParamTypes[i];
			Class requestParamType = findRequestParamType(methodParamType);
			args[i] = paramTypeToValueMapping.get(requestParamType);
		}
		return args;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param request
	 * @return
	 */
	private Map getParamMap(ServletRequest request) {
		Map paramMapping = new HashMap();
		Enumeration enumer = request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String paramName = (String) enumer.nextElement();
			String paramValue = request.getParameter(paramName);
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length > 1) {
				paramMapping.put(paramName, paramValues);
			} else {
				paramMapping.put(paramName, paramValue);
			}
		}
		return paramMapping;
	}

	/**
	 * ���ݷ����������ͻ�ȡ�󶨵������������
	 * 
	 * @param methodParamType
	 * @return
	 */
	private Class findRequestParamType(Class methodParamType) {
		if (methodParamTypeToRequestParamTypeMapping.containsKey(methodParamType))
			return (Class) methodParamTypeToRequestParamTypeMapping.get(methodParamType);

		bindRequestParamType(methodParamType, new Class[] { User.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class,
				HttpSession.class, ServletContext.class, HashMap.class, ActionForm.class });
		return (Class) methodParamTypeToRequestParamTypeMapping.get(methodParamType);
	}

	private void bindRequestParamType(Class methodParamType, Class[] candidateTypes) {
		for (int i = 0; i < candidateTypes.length; i++) {
			if (methodParamType.isAssignableFrom(candidateTypes[i])) {
				methodParamTypeToRequestParamTypeMapping.put(methodParamType, candidateTypes[i]);
			}
		}
	}
	
	//======================================���ݲ����������==========================================

	/**
	 * ���ݲ���ǰ׺�������Ͷ���������������ָ��Class�Ķ���
	 *
	 * @param request
	 * @param targetClass
	 * @param paramPrefix
	 * @param index
	 * @param propNames
	 * @return
	 */
	protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix, int index, String[] propNames) {
		String seperator = index < 0 ? "." : ("[" + index + "].");
		String[] propValues = new String[propNames.length];
		for (int i = 0; i < propNames.length; i++) {
			propValues[i] = request.getParameter(paramPrefix + seperator + propNames[i]);
		}

		Object target = null;

		if (!Tools.isAllEmpty(propValues)) {
			try {
				target = targetClass.newInstance();
				for (int i = 0; i < propNames.length; i++) {
					if (propValues[i] != null) {
						Tools.setPropValue(target, propNames[i], propValues[i]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	/**
	 * ���ݲ���ǰ׺�Ͷ���������������ָ��Class�Ķ���
	 *
	 * @param request
	 * @param targetClass
	 *            Ҫ����Ķ���Class
	 * @param paramPrefix
	 * @param propNames
	 * @return
	 */
	protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix, String[] propNames) {
		return buildObjectByParam(request, targetClass, paramPrefix, -1, propNames);
	}

	/**
	 * ���ݲ���ǰ׺�������Ͷ���������������ָ��Class�Ķ���
	 *
	 * @param request
	 * @param targetClass
	 * @param paramPrefix
	 * @param index
	 * @param propNames
	 * @return
	 */
	protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix, int index) {
		String[] propNames = ClassUtils.getPropNames(targetClass);
		return buildObjectByParam(request, targetClass, paramPrefix, index, propNames);
	}

	/**
	 * ���ݲ���ǰ׺�Ͷ���������������ָ��Class�Ķ���
	 *
	 * @param request
	 * @param targetClass
	 *            Ҫ����Ķ���Class
	 * @param paramPrefix
	 * @return
	 */
	protected Object buildObjectByParam(HttpServletRequest request, Class targetClass, String paramPrefix) {
		return buildObjectByParam(request, targetClass, paramPrefix, -1);
	}

	protected List buildObjectListByParam(HttpServletRequest request, Class targetClass, String paramPrefix){
		return buildObjectListByParam(request, targetClass, paramPrefix, null, null);
	}

	protected Map findParamValueByPrefix(HttpServletRequest request,String prefix) {
		Map data=new HashMap();
		Enumeration enumer=request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String paramName = (String) enumer.nextElement();
			if(paramName.startsWith(prefix)){
				data.put(paramName.substring(prefix.length()), request.getParameter(paramName));
			}
		}
		return data;
	}

	/**
	 * ���ݲ�������ǰ׺�������󼯺�
	 *
	 * @param request
	 * @param targetClass
	 * @param paramPrefix
	 * @param errorMessage
	 * @param messageFormat
	 * @return
	 */
	protected List buildObjectListByParam(HttpServletRequest request, Class targetClass, String paramPrefix,List errorMessage,String messageFormat){
		List validatableList = new ArrayList();
		int size = findMaxListSizeByParamPrefix(request, paramPrefix);
		for (int i = 0; i < size; i++) {
			Object builded = buildObjectByParam(request, targetClass, paramPrefix, i);
			//��Ȼ����Ϊ�վ�Ҫ��֤ͨ��������������Ϊ�ղ���Ч
			if(builded!=null&&builded instanceof Validatable){
				if(builded instanceof Validatable){
					if(!((Validatable) builded).isValidate()){
						if (Tools.isNotEmpty(messageFormat) && errorMessage!=null) {
							errorMessage.add(MessageFormat.format(messageFormat, new Object[] { new Integer(i+1) }));
						}
					}
					validatableList.add(builded);
				}
			}
		}
		return validatableList;
	}

	/**
	 * ����ָ����������ǰ׺�ļ��ϴ�С
	 *
	 * @param request
	 * @param paramPrefix
	 * @return
	 */
	private static int findMaxListSizeByParamPrefix(HttpServletRequest request, String paramPrefix){
		Pattern pattern=Pattern.compile("^"+paramPrefix+"\\[(\\d+?)\\]\\.\\w+$");
		int size=0;
		Enumeration enumer=	request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String paramName = (String) enumer.nextElement();
			Matcher matcher= pattern.matcher(paramName);
			if(matcher.find()){
				String index=matcher.group(1);
				size=Math.max(size, Integer.parseInt(index)+1);
			}
		}
		return size;
	}
}