package com.rokid.soa.common;



import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rokid.soa.bo.report.AsrReport;


/**
 * ReadCreatePdf
 **/
public class PdfExportUtils {
	//PDF页面大小设定
	Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

	/**
	 * ToPdfTable
	 * @author caokf
	 * @param pdfname
	 * @param outputpath
	 * @param pdfwidth
	 * @param pdfheader
	 * @param pdfmeisa
	 * 注：封装databean顺序势必与头部数据保持一致
	 * 注：pdfmeisa为 ArrayList
	 * @param fos
	 * @param title
	 *
	 */
	public void ToPdfTable( float[] pdfwidth,
	                        String[] pdfheader,ArrayList<AsrReport> pdfmeisa,FileOutputStream fos, String title) throws DocumentException,IOException, URISyntaxException  {

		PdfWriter.getInstance(document, fos);

		//System.out.println(path);
		try {;
			// 中文格式设定
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 9, Font.NORMAL);
			document.open();

			int NumColumns = 0;

			Paragraph titlepa = new Paragraph(title,FontChinese);
			titlepa.setAlignment(Element.ALIGN_CENTER);
			titlepa.setSpacingAfter(20);
			document.add(titlepa);

			PdfPTable datatable = null;
			NumColumns=pdfheader.length;
			// PDF表格设定开始
			datatable = new PdfPTable(NumColumns);
			if(pdfwidth!=null&&pdfwidth.length==NumColumns){
				datatable.setWidths(pdfwidth);
			}
			datatable.getDefaultCell().setPadding(3);
			datatable.getDefaultCell().setBorderWidth(2);
			datatable.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			for (int w = 0; w < NumColumns; w++) {
				datatable.addCell(new Phrase(pdfheader[w], FontChinese));
			}

			// PDF表格头部设定结束
			datatable.setHeaderRows(1);


			// PDF表格内容设定
			datatable.getDefaultCell().setBorderWidth(1);
			/** 此bean为pdf封装对象bean*/
			AsrReport pdfRow = new AsrReport();
			for(int x = 0; x < pdfmeisa.size(); x++) {
				if (x % 2 == 1) {
					datatable.getDefaultCell().setGrayFill(0.9f);
				}
				pdfRow = pdfmeisa.get(x);
				// 获取实体类的所有属性，返回Field数组
				Field[] fields = pdfRow.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++){
					//获取类属性类型
					String type = fields[i].getGenericType().toString();
					Field f = fields[i];
					//设置些属性是可以访问的
					f.setAccessible(true);
					//得到类属性的值
					Object val = f.get(pdfRow);
					//得到类属性名称
					String name = f.getName();
					/** 明细部分pdf值设定START*/
					if(name.equals("time")||name.equals("sn")||name.equals("asr")||name.equals("asrEdit")){
						if(val==null){
							datatable.addCell(new Phrase("", FontChinese));
						}else{
							datatable.addCell(new Phrase(val.toString(), FontChinese));
						}
					}
					if(name.equals("type")&&val!=null){
						//字段为声音类型&&类型为男
						if(name.equals("type")&& val.equals((short)8)){
							datatable.addCell(new Phrase("男", FontChinese));
							//字段为声音类型&&类型为女
						}else if(name.equals("type")&&val.equals((short)4)){
							datatable.addCell(new Phrase("女", FontChinese));
							//字段为声音类型&&类型为幼
						}else if(name.equals("type")&&val.equals((short)2)){
							datatable.addCell(new Phrase("幼", FontChinese));
						}else{
							datatable.addCell(new Phrase(val.toString(), FontChinese));
						}
					}else if(name.equals("type")&&val==null){
						datatable.addCell(new Phrase("", FontChinese));
					}
					/** 明细部分pdf值设定END*/
				}
				if (x % 2 == 1) {
					datatable.getDefaultCell().setGrayFill(1);
				}
			}
			document.add(datatable);
		} catch (Exception de) {
			System.out.println("目标文件不存，或者不可读！");
			de.printStackTrace();
		}finally {
			document.close();
		}
	}

}