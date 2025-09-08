package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtils {

    private static Map<String, Workbook> workbookCache = new HashMap<>();

    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            Workbook workbook = getWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = (cell != null) ? getCellValueAsString(cell) : "";
                    rowData.put(headers.get(j), value);
                }
                data.add(rowData);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath + ", Sheet: " + sheetName, e);
        }

        return data;
    }

    public static Map<String, String> getRowByValue(String filePath, String sheetName, String columnName, String value) {
        List<Map<String, String>> allData = readExcelData(filePath, sheetName);

        return allData.stream()
                .filter(row -> value.equals(row.get(columnName)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format("No row found with %s='%s' in %s:%s", columnName, value, filePath, sheetName)
                ));
    }

    public static Map<String, String> getLoginCredentials(String userType) {
        return getRowByValue("testData/TestData.xlsx", "LoginData", "UserType", userType);
    }

    public static Map<String, String> getEmployeeData(String scenario) {
        return getRowByValue("testData/TestData.xlsx", "EmployeeData", "Scenario", scenario);
    }

    public static Map<String, String> getNavigationData(String module) {
        return getRowByValue("testData/TestData.xlsx", "NavigationData", "Module", module);
    }

    private static Workbook getWorkbook(String filePath) throws IOException {
        if (!workbookCache.containsKey(filePath)) {
            String fullPath = "src/test/resources/" + filePath;
            FileInputStream fis = new FileInputStream(fullPath);
            Workbook workbook = new XSSFWorkbook(fis);
            workbookCache.put(filePath, workbook);
            fis.close();
        }
        return workbookCache.get(filePath);
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    public static Map<String, String> getLeaveData(String scenario) {
        return getRowByValue("testData/TestData.xlsx", "LeaveData", "Scenario", scenario);
    }

    public static void closeWorkbooks() {
        workbookCache.values().forEach(workbook -> {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("Failed to close workbook: " + e.getMessage());
            }
        });
        workbookCache.clear();
    }
}