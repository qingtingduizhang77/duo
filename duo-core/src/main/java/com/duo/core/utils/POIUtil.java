package com.duo.core.utils;

import com.duo.core.BaseService;
import com.duo.modules.tool.entity.ToolExcelImportColumn;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/12/21]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class POIUtil {

//    /**
//     * 导出excel
//     *
//     * @param response
//     * @param header
//     * @param dataList
//     * @throws Exception
//     */
//    public static void exportExcel(HttpServletResponse response, List<String> header, List<List<String>> dataList) throws Exception {
//        exportExcel(response, "主标题", "副标题", header, dataList);
//    }
//
//    /**
//     * 导出excel
//     *
//     * @param response
//     * @param title
//     * @param subheading
//     * @param header
//     * @param dataList
//     * @throws Exception
//     */
//    public static void exportExcel(HttpServletResponse response, String title, String subheading, List<String> header, List<List<String>> dataList) throws Exception {
//        //获取一个HSSFWorkbook对象
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFCellStyle style = getHSSFCellStyle(workbook);
//        //创建一个sheet
//        HSSFSheet sheet = workbook.createSheet("Sheet1");
//        //创建一个标题行
//        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, header.size());
//        //创建一个副标题行
//        CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 0, header.size());
//        sheet.addMergedRegion(cellRangeAddress);
//        sheet.addMergedRegion(cellRangeAddress2);
//
//        //标题，居中
//        HSSFRow row0 = sheet.createRow(0);
//        HSSFCell cell0 = row0.createCell(0);
//        cell0.setCellValue(title);
//        cell0.setCellStyle(style);
//        // 第一行
//        HSSFRow row1 = sheet.createRow(1);
//        HSSFCell cell1 = row1.createCell(0);
//        //副标题
//        cell1.setCellValue(subheading);
//        cell1.setCellStyle(style);
//
//        //表头
//        HSSFRow row = sheet.createRow(2);
//
//        HSSFCell cell = null;
//        for (int i = 0; i < header.size(); i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(header.get(i));
//            cell.setCellStyle(style);
//        }
//
//        //数据
//        for (int i = 0; i < dataList.size(); i++) {
//            row = sheet.createRow(i + 3);
//            for (int j = 0; j < dataList.get(i).size(); j++) {
//                row.createCell(j).setCellValue(dataList.get(i).get(j));
//            }
//        }
//
//        OutputStream outputStream = response.getOutputStream();
//        //设置页面不缓存
//        response.reset();
//        String filename = title;
//        //设置返回文件名的编码格式
//        response.setCharacterEncoding("utf-8");
//        filename = URLEncoder.encode(filename, "utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");
//        response.setContentType("application/msexcel");
//        workbook.write(outputStream);
//        outputStream.close();
//    }

//    /**
//     * 创建一个style
//     *
//     * @param workbook
//     * @return
//     */
//    private static HSSFCellStyle getHSSFCellStyle(HSSFWorkbook workbook) {
//        HSSFCellStyle style = workbook.createCellStyle();
//        //居中
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        return style;
//    }


}
