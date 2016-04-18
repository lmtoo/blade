package cn.accessbright.blade.core.excel;

import com.icitic.hrms.core.util.CompositeRegion;
import com.icitic.hrms.core.util.Point;

 /**
	* ��ʾһ��excel����ͷ������<br>
	* �����÷���
	* 	
	*			ExcelHeader header=new ExcelHeader(65, 3);
	*			
	*			header
	*			.maxHeight(new String[] {
	*				"���", "��λ����", "��������", "��н��λ", "����", "����", "���֤��", "�ù����", "���񹤷���	", "��λ", "����", "��λ", "ְ��", "��Ա�ȼ�(н��)","н��",
	*				"����ʱ��", "����ʱ��", "	����ʱ��", "��н�·���", "˰ǰ�ϼ�=Ӧ����","˰��ϼ�=ʵ����","˰���¾�����=ʵ����/��н�·���"
	*			})
	*			
	*			.sub(6)
	*			.maxWidth("������𲿷�")
	*			.maxHeight(new String[] { 
	*					"�������ϱ���(����) ����(�������Ͻ�)", "����ҽ�Ʊ���(����) ����(ҽ�Ʊ��ս�)", "ʧҵ����(����)  ����(ʧҵ���ս�)", "ס��������(����) ����(ס��������)",
	*					"����ס��������(����) ����(���乫����)", "�������ϼ�"
	*			}).end()
	*			
	*			.sub(8)
	*			.maxWidth("��λ�߽𲿷�")
	*			.maxHeight(new String[] { 
	*					"���ϱ���(��λ)  ����(�������Ͻ�) ", "ҽ�Ʊ���(��λ) ����(ҽ�Ʊ��ս�) ", "ʧҵ����(��λ) ����(ʧҵ���ս�)", "���˱���(��λ) ����(���˱��ս�)",
	*					 "��������(��λ) ����(�������ս�)", "����������(��λ) ����(ס��������)	", "��������(��λ) ����(����������)", "��λ�߽�ϼ�" 
	*			}).end()
	*			
	*			.maxHeight(new String[]{
	*					"��˰�ϼ�=��˰+���˸�˰", "��ҵ�����˲���=��ҵ��𣨸��ˣ�+������ҵ�����˲���", 
	*					year+"����ҵ���˰ǰ������", year+"��ס�����������",  year+"ס�������״η��Ž��",  year+"ס������ʣ�෢�Ž��", 
	*					 year+"�겹��ҽ�Ʒ����", "˰ǰ�¾�����=Ӧ����/��н�·���", "��λ�䶯��Ϣ	", "��λ�䶯��Ϣ��ע"
	*			})
	*			
	*			.sub(1)
	*			.minimize("������")
	*			.maxHeight("=ȫ�ꡰ��λ�������ʡ��ϼ���")
	*			.end()
	*			
	*			.sub(7)
	*			.maxWidth("������")
	*			.maxHeight(new String[]{"�����", "���˽���", "��������", "�ڿؿ��˽���", "��֧�н���", "ȫ�н���", "����ϼ�"})
	*			.end()
	*			
	*			.sub(3)
	*			.minimize(new String[]{"���ڷ���", "ס�����������ս���", "�Ӱ๤��"})
	*			.maxHeight(new String[]{"��ȫ�������ڷѺϼ���", preYear+"��ʣ�෿�������ս����ڷ��Ų���", "��ȫ��Ӱ�Ѻϼ���"})
	*			.end()
	*			
	*			.sub(6)
	*			.maxWidth("������").minimize(new String[]{"ͨѶ��","���·�"})
	*			.maxHeight("���Ϸѣ�����������").width(2, "����")
	*			.minimize(new String[]{"�����ϼ�","��ȫ�ꡰͨѶ�ѡ��ϼ�����","��ȫ�ꡰ���·ѡ��ϼ�����","������Ů��","�����������","=ͨѶ��+���·�+���Ϸ����������Ѻ��Ͷ�������+����"})
	*			.end()
	*			
	*			.sub(1)
	*			.minimize(new String[]{"","����Ѻϼ�","=ȫ�깤��Ѻϼ���"})
	*			.end()
	*
	*			.maxHeight("");
	*
	*			��һ��д��������ʹ�ô��÷���
	*
	*			final boolean hasContentLine=YearEvaEmp.hasContentLine(type);
	*			ExcelHeader header=new ExcelHeader(8+4*3+(hasContentLine?1:0),2){{
	*					sub(4).maxHeight(new String[]{"���","����","����","ְ��"});
	*					if(hasContentLine)maxHeight("���߹�������");
	*					sub(4).maxHeight(new String[]{"��ְ��Ա����Ȩ����","�������(��ְ��Ա��)","��ְ��Ȩ����","�������(��ְ)"});
	*					sub(4).maxWidth("ʹ�ý��飨Ա����").minimize(new String[]{"���","����","��ְ","��������"});
	*					sub(4).maxWidth("ʹ�ý��飨��ְ��").minimize(new String[]{"���","����","��ְ","��������"});
	*					sub(4).maxWidth("ʹ�ý��飨��ְ��").minimize(new String[]{"���","����","��ְ","��������"});
	*			}};
	*						
	* @author ll
	* 
    */
public class ExcelHeader extends CompositeRegion {
	public ExcelHeader(Point coordinate, int width, int height) {
		super(coordinate, width, height);
	}

	/**
	 * ��ͷ
	 * @param row ��ͷ����ʼ������
	 * @param width ��ͷ���
	 * @param height ��ͷ�߶�
	 */
	public ExcelHeader(int row, int width, int height) {
		this(new Point(0, row), width, height);
	}

	public ExcelHeader(int width, int height) {
		this(new Point(0,0), width, height);
	}

	/**
	 * ���ñ�ͷ����ʼ����
	 * 
	 * @param rowIndex
	 */
	public void resetRowIndex(int rowIndex){
		this.relative = new Point(this.relative.left(), rowIndex);
	}
	
	/**
	 * ���ñ�ͷ����ʼ����
	 * 
	 * @param rowIndex
	 */
	public void resetColIndex(int colIndex){
		this.relative=new Point(colIndex, this.relative.top());
	}
	
	/**
	 * ���¶�λ��ͷ��λ��
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void relocate(int rowIndex,int colIndex){
		this.relative=new Point(colIndex, rowIndex);
	}
}