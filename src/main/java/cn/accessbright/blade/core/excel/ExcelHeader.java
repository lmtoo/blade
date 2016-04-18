package cn.accessbright.blade.core.excel;


import cn.accessbright.blade.core.CompositeRegion;
import cn.accessbright.blade.core.Point;

/**
 * 表示一个excel表格的头部区域<br>
 * 具体用法：
 *
 *			ExcelHeader header=new ExcelHeader(65, 3);
 *
 *			header
 *			.maxHeight(new String[] {
 *				"序号", "岗位分类", "机构分类", "发薪单位", "工号", "姓名", "身份证号", "用工类别", "劳务工分类	", "单位", "部门", "岗位", "职务", "行员等级(薪酬)","薪档",
 *				"入行时间", "离行时间", "	退休时间", "计薪月份数", "税前合计=应发数","税后合计=实发数","税后月均收入=实发数/计薪月份数"
 *			})
 *
 *			.sub(6)
 *			.maxWidth("个人五金部分")
 *			.maxHeight(new String[] {
 *					"基本养老保险(个人) 补扣(基本养老金)", "基本医疗保险(个人) 补扣(医疗保险金)", "失业保险(个人)  补扣(失业保险金)", "住房公积金(个人) 补扣(住房公积金)",
 *					"补充住房公积金(个人) 补扣(补充公积金)", "个人五金合计"
 *			}).end()
 *
 *			.sub(8)
 *			.maxWidth("单位七金部分")
 *			.maxHeight(new String[] {
 *					"养老保险(单位)  补扣(基本养老金) ", "医疗保险(单位) 补扣(医疗保险金) ", "失业保险(单位) 补扣(失业保险金)", "工伤保险(单位) 补扣(工伤保险金)",
 *					 "生育保险(单位) 补扣(生育保险金)", "基本公积金(单位) 补扣(住房公积金)	", "其他保险(单位) 补扣(其他公积金)", "单位七金合计"
 *			}).end()
 *
 *			.maxHeight(new String[]{
 *					"个税合计=个税+清退个税", "企业年金个人部分=企业年金（个人）+清退企业年金个人部分",
 *					year+"年企业年金税前分配金额", year+"年住房补贴分配额",  year+"住房补贴首次发放金额",  year+"住房补贴剩余发放金额",
 *					 year+"年补充医疗分配额", "税前月均收入=应发数/计薪月份数", "岗位变动信息	", "岗位变动信息备注"
 *			})
 *
 *			.sub(1)
 *			.minimize("工资类")
 *			.maxHeight("=全年“岗位基本工资”合计数")
 *			.end()
 *
 *			.sub(7)
 *			.maxWidth("奖金类")
 *			.maxHeight(new String[]{"定额奖金", "考核奖金", "其他奖金", "内控考核奖金", "分支行奖金", "全行奖金", "奖金合计"})
 *			.end()
 *
 *			.sub(3)
 *			.minimize(new String[]{"过节费类", "住房补贴（年终奖）", "加班工资"})
 *			.maxHeight(new String[]{"＝全年各类过节费合计数", preYear+"年剩余房帖在年终奖金内发放部分", "＝全年加班费合计数"})
 *			.end()
 *
 *			.sub(6)
 *			.maxWidth("福利类").minimize(new String[]{"通讯费","高温费"})
 *			.maxHeight("资料费，视力保护费").width(2, "其他")
 *			.minimize(new String[]{"福利合计","＝全年“通讯费”合计数”","＝全年“高温费”合计数”","独生子女费","少数民族津贴","=通讯费+高温费+资料费视力保护费和劳动防护费+其他"})
 *			.end()
 *
 *			.sub(1)
 *			.minimize(new String[]{"","工会费合计","=全年工会费合计数"})
 *			.end()
 *
 *			.maxHeight("");
 *
 *			另一种写法（建议使用此用法）
 *
 *			final boolean hasContentLine=YearEvaEmp.hasContentLine(type);
 *			ExcelHeader header=new ExcelHeader(8+4*3+(hasContentLine?1:0),2){{
 *					sub(4).maxHeight(new String[]{"年度","姓名","部门","职务"});
 *					if(hasContentLine)maxHeight("条线管理内容");
 *					sub(4).maxHeight(new String[]{"副职及员工加权分数","打分人数(副职及员工)","正职加权分数","打分人数(正职)"});
 *					sub(4).maxWidth("使用建议（员工）").minimize(new String[]{"提拔","交流","降职","不作调整"});
 *					sub(4).maxWidth("使用建议（副职）").minimize(new String[]{"提拔","交流","降职","不作调整"});
 *					sub(4).maxWidth("使用建议（正职）").minimize(new String[]{"提拔","交流","降职","不作调整"});
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
	 * 表头
	 * @param row 表头的起始行索引
	 * @param width 表头宽度
	 * @param height 表头高度
	 */
	public ExcelHeader(int row, int width, int height) {
		this(new Point(0, row), width, height);
	}

	public ExcelHeader(int width, int height) {
		this(new Point(0,0), width, height);
	}

	/**
	 * 重置表头行起始索引
	 *
	 * @param rowIndex
	 */
	public void resetRowIndex(int rowIndex){
		this.relative = new Point(this.relative.left(), rowIndex);
	}

	/**
	 * 重置表头列起始索引
	 *
	 * @param colIndex
	 */
	public void resetColIndex(int colIndex){
		this.relative=new Point(colIndex, this.relative.top());
	}

	/**
	 * 重新定位表头的位置
	 *
	 * @param rowIndex
	 * @param colIndex
	 */
	public void relocate(int rowIndex,int colIndex){
		this.relative=new Point(colIndex, rowIndex);
	}
}