package com.jstech.fluenterp;

/**
 * Central registry of backend API endpoints.
 *
 * To switch backends, change BASE_URL only — every URL constant below is derived from it.
 */
public final class Constants {

    private Constants() {}

    /** Base URL of the PHP/MySQL backend. Include trailing slash. */
    public static final String BASE_URL = "https://your-server.com/";

    // --- Master Data: Customers ---
    public static final String URL_CREATE_CUSTOMER          = BASE_URL + "createCustomer.php";
    public static final String URL_RETRIEVE_CUSTOMERS       = BASE_URL + "retrieveU.php";
    public static final String URL_RETRIEVE_CUSTOMER_BY_ID  = BASE_URL + "retrieveCustomerById.php";
    public static final String URL_RETRIEVE_CUSTOMERS_WC    = BASE_URL + "retrieveUWC.php";
    public static final String URL_MODIFY_CUSTOMER          = BASE_URL + "modifyCustomer.php";

    // --- Master Data: Employees ---
    public static final String URL_CREATE_EMPLOYEE              = BASE_URL + "createEmployee.php";
    public static final String URL_RETRIEVE_EMPLOYEES           = BASE_URL + "retrieveEmployees.php";
    public static final String URL_RETRIEVE_EMPLOYEE_BY_ID      = BASE_URL + "retrieveEmployeeById.php";
    public static final String URL_RETRIEVE_EMPLOYEE_BY_NUMBER  = BASE_URL + "retrieveEmployeeByNumber.php";
    public static final String URL_RETRIEVE_EMPLOYEE_BY_TYPE    = BASE_URL + "retrieveEmployeeByType.php";
    public static final String URL_RETRIEVE_EMPLOYEE_BY_DATES   = BASE_URL + "retrieveEmployeeByDates.php";
    public static final String URL_MODIFY_EMPLOYEE              = BASE_URL + "modifyEmployee.php";

    // --- Sales & Distribution ---
    public static final String URL_CREATE_SALES_DOC                        = BASE_URL + "createSalesDoc.php";
    public static final String URL_CREATE_SALES_ORDER                      = BASE_URL + "createSalesOrder.php";
    public static final String URL_RETRIEVE_SALES_ORDERS                   = BASE_URL + "retrieveSalesOrder.php";
    public static final String URL_RETRIEVE_SALES_ORDER_WITH_DOC           = BASE_URL + "retrieveSalesOrderWithDoc.php";
    public static final String URL_FILTER_SALES_ORDERS                     = BASE_URL + "filterRetrieveSalesOrder.php";
    public static final String URL_FILTER_SALES_ORDERS_BY_CUSTOMER         = BASE_URL + "filterSalesOrderWithCustomer.php";
    public static final String URL_FILTER_SALES_ORDERS_BY_DOC              = BASE_URL + "filterSalesOrderWithSalesDocument.php";
    public static final String URL_FILTER_SALES_ORDERS_BY_ONE_DATE         = BASE_URL + "filterSalesOrderWithOneDate.php";
    public static final String URL_FILTER_SALES_ORDERS_BY_TWO_DATES        = BASE_URL + "filterSalesOrderWithTwoDates.php";
    public static final String URL_FILTER_SALES_ORDERS_BY_STATUS           = BASE_URL + "filterSalesOrderWithOrderStatus.php";
    public static final String URL_MODIFY_SALES_DOC                        = BASE_URL + "modifySalesDoc.php";
    public static final String URL_MODIFY_SALES_ORDER                      = BASE_URL + "modifySalesOrder.php";
    public static final String URL_LOAD_SALES_ORDER_NUMBERS                = BASE_URL + "loadSalesOrderNumbers.php";

    // --- Purchasing ---
    public static final String URL_CREATE_PURCHASE_DOC                         = BASE_URL + "createPurchaseDoc.php";
    public static final String URL_CREATE_PURCHASE_ORDER                       = BASE_URL + "createPurchaseOrder.php";
    public static final String URL_RETRIEVE_PURCHASE_ORDERS                    = BASE_URL + "retrievePurchaseOrders.php";
    public static final String URL_RETRIEVE_PURCHASE_ORDER_WITH_DOC            = BASE_URL + "retrievePurchaseOrderWithDoc.php";
    public static final String URL_RETRIEVE_PURCHASE_ORDER_WITH_PURCHASE_DOC   = BASE_URL + "retrievePurchaseOrderWithPurchaseDocument.php";
    public static final String URL_MODIFY_PURCHASE_DOC                         = BASE_URL + "modifyPurchaseDoc.php";
    public static final String URL_MODIFY_PURCHASE_ORDER                       = BASE_URL + "modifyPurchaseOrder.php";
    public static final String URL_LOAD_PURCHASE_ORDER_NUMBERS                 = BASE_URL + "loadPurchaseOrderNumbers.php";
    public static final String URL_RETRIEVE_SELLERS                            = BASE_URL + "retrieveSellers.php";
    public static final String URL_LOAD_UG_MATERIALS                           = BASE_URL + "loadUGMaterials.php";

    // --- Dispatch & Delivery ---
    public static final String URL_RETRIEVE_ORDER_STATUS          = BASE_URL + "retrieveOrderStatus.php";
    public static final String URL_CHANGE_ORDER_STATUS            = BASE_URL + "changeOrderStatus.php";
    public static final String URL_RETRIEVE_ORDER_STATUS_PURCHASE = BASE_URL + "retrieveOrderStatusPurchase.php";
    public static final String URL_CHANGE_ORDER_STATUS_PURCHASE   = BASE_URL + "changeOrderStatusPurchase.php";

    // --- Material Management ---
    public static final String URL_CREATE_MATERIAL          = BASE_URL + "createMaterial.php";
    public static final String URL_LOAD_MATERIALS           = BASE_URL + "loadMaterials.php";
    public static final String URL_RETRIEVE_MATERIAL_BY_ID  = BASE_URL + "retrieveMaterialById.php";
    public static final String URL_MODIFY_MATERIAL          = BASE_URL + "modifyMaterial.php";
    public static final String URL_RETRIEVE_CURRENT_STOCK   = BASE_URL + "retrieveCurrentStock.php";

    // --- Charts / Reports ---
    public static final String URL_RETRIEVE_CHART_PIE  = BASE_URL + "retrieveChartResults.php";
    public static final String URL_RETRIEVE_CHART_BAR  = BASE_URL + "retrieveChartResultsBar.php";
    public static final String URL_REPORT_CUSTOMERS    = BASE_URL + "createCustomerReport.php";
    public static final String URL_REPORT_MATERIALS    = BASE_URL + "createMaterialReport.php";
    public static final String URL_REPORT_PRICES       = BASE_URL + "createPriceReport.php";
    public static final String URL_CREATE_INVOICE      = BASE_URL + "createInvoice.php";

    // --- Account / Auth ---
    public static final String URL_CHECK_LOGIN          = BASE_URL + "checkLogin.php";
    public static final String URL_REGISTER_ACCOUNT     = BASE_URL + "registerNewAccount.php";
    public static final String URL_UPDATE_ACCOUNT       = BASE_URL + "updateExistingAccount.php";

    /** Prefix for opening a PHP-generated PDF inside a WebView via Google Docs Viewer. */
    public static final String GDOCS_VIEWER_PREFIX = "http://docs.google.com/gview?embedded=true&url=";
}
