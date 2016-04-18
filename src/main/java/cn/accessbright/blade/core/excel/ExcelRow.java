package cn.accessbright.blade.core.excel;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.icitic.hrms.sys.pojo.bo.InfoItemBO;

/**
 * Excel�в���API
 * 
 * @author ll
 *
 */
public interface ExcelRow {
	//�ı����ڵ�Ԫ���λ��
	int TEXT_ALIGN_ORIGNAL=-1;
	//�������
	int TEXT_ALIGN_LEFT = 0;
	//���Ҷ���
	int TEXT_ALIGN_RIGHT = 1;
	//���ж���
	int TEXT_ALIGN_CENTER = 2;

	// =================================�����ַ���ֵ=================================
	ExcelRow addString(String value);
	ExcelRow addString(Object value);
	ExcelRow addString(int value);

	ExcelRow addString(String prop, Object target);
	ExcelRow addString(String[] props, Object target);
	
	ExcelRow addString(String[] values);
	ExcelRow addString(List values);
	ExcelRow addString(Object[] values);
	ExcelRow addString(Object[] values, int start);
	ExcelRow addString(Object[] values, int start, int end);
	

	ExcelRow addString(String key, Map values);
	ExcelRow addString(List keys, Map values);
	ExcelRow addString(String[] keys, Map values);

	ExcelRow addString(String key, Map values, boolean ignoreKeyCase);
	ExcelRow addString(List keys, Map values, boolean ignoreKeyCase);

	// =================================�����ַ���ֵ=================================
	ExcelRow addString(String value, int align);
	ExcelRow addString(String[] values, int align);
	ExcelRow addString(List values, int align);

	ExcelRow addString(String key, Map values, int align);
	ExcelRow addString(List keys, Map values, int align);

	ExcelRow addString(String key, Map values, boolean ignoreKeyCase, int align);
	ExcelRow addString(List keys, Map values, boolean ignoreKeyCase, int align);

	// =================================��������ֵ=================================
	ExcelRow addNumber(double value);// Ĭ�ϱ�����λС��
	ExcelRow addNumber(String value);// Ĭ�ϱ�����λС��
	ExcelRow addNumber(Object value);// Ĭ�ϱ�����λС��
	ExcelRow addNumber(Object[] values);
	ExcelRow addNumber(Object[] values, int begin);
	ExcelRow addNumber(Object[] values, int begin, int length);
	
	
	ExcelRow addNumber(String key, Map values);
	ExcelRow addNumber(List keys, Map values);
	ExcelRow addNumber(String[] keys, Map values);
	ExcelRow addNumber(Iterator keysIter, Map values);

	ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase);
	ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase);
	ExcelRow addNumber(Iterator keysIter, Map values, boolean ignoreKeyCase);

	// ָ������С��λ��
	ExcelRow addNumber(double value, int digits);
	ExcelRow addNumber(String value, int digits);

	ExcelRow addNumber(String key, Map values, int digits);
	ExcelRow addNumber(List keys, Map values, int digits);

	ExcelRow addNumber(String key, Map values, boolean ignoreKeyCase, int digits);
	ExcelRow addNumber(List keys, Map values, boolean ignoreKeyCase, int digits);

	// =================================��������ֵ=================================

	ExcelRow addInteger(int value);
	ExcelRow addInteger(Integer value);
	ExcelRow addInteger(String value);

	ExcelRow addInteger(String key, Map values);
	ExcelRow addInteger(List keys, Map values);

	ExcelRow addInteger(String key, Map values, boolean ignoreKeyCase);
	ExcelRow addInteger(List keys, Map values, boolean ignoreKeyCase);

	// =================================���ô���ֵ=================================
	ExcelRow addCode(InfoItemBO info, String value);
	ExcelRow addCode(Object value, String codeType);
	ExcelRow addCode(String value, String codeType);
	
	ExcelRow addCode(String key, Map values, String codeType);
	ExcelRow addCode(String[] keys, Map values, String codeType);

	ExcelRow addCode(InfoItemBO info, Map values, boolean ignoreKeyCase);
	ExcelRow addCode(List infos, Map values, boolean ignoreKeyCase);
	ExcelRow addCode(String key, Map values, String codeType, boolean ignoreKeyCase);
	ExcelRow addCode(String[] keys, Map values, String codeType, boolean ignoreKeyCase);
	
		

	// =================================�ϲ���Ԫ������ֵ=================================
	/**
	 * �ϲ���ǰ����ָ������֮��ĵ�Ԫ�񣬲�����ֵ
	 * 
	 * @param from
	 *            ��ʼ��Ԫ������
	 * @param to
	 *            ������Ԫ������
	 * @param value
	 *            �����õ�ֵ
	 * @return
	 */
	ExcelRow mergeString(int from, int to, String value);

	/**
	 * �ϲ���ǰ���е�ǰ��Ԫ��֮��ָ�����ȵĵ�Ԫ�񣬲�����ֵ
	 * 
	 * @param length
	 *            Ҫ�ϲ��ĵ�Ԫ��ĳ���
	 * @param value
	 *            �����õ�ֵ
	 * @return
	 */
	ExcelRow mergeString(int length, String value);
	
	/**
	 * �ϲ���ǰ����ָ������֮��ĵ�Ԫ�񣬲�����ֵ
	 * 
	 * @param from
	 *            ��ʼ��Ԫ������
	 * @param to
	 *            ������Ԫ������
	 * @param value
	 *            �����õ�ֵ
	 * @return
	 */
	ExcelRow mergeString(int from, int to, String value, int align);

	/**
	 * �ϲ���ǰ���е�ǰ��Ԫ��֮��ָ�����ȵĵ�Ԫ�񣬲�����ֵ
	 * 
	 * @param length
	 *            Ҫ�ϲ��ĵ�Ԫ��ĳ���
	 * @param value
	 *            �����õ�ֵ
	 * @return
	 */
	ExcelRow mergeString(int length, String value, int align);
}