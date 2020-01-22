# FluentERP

An Android ERP application covering the core modules of Enterprise Resource Planning — Sales & Distribution, Material Management, Purchasing, Human Resources, Dispatch & Delivery, and Master Data — inspired by SAP SD workflows.

- **minSdkVersion:** 23 (Android 6.0 Marshmallow)
- **targetSdkVersion:** 35 (Android 15)
- **Language:** Java

## Architecture

```
app/src/main/java/com/jstech/fluenterp/
├── sd/           — Sales & Distribution (create / modify / display / list sales orders)
├── mm/           — Material Management (materials CRUD, current stock)
├── purchasing/   — Purchase orders, seller list, order status changes
├── hr/           — Human Resources (employee list and filtering)
├── dd/           — Dispatch & Delivery (check/change order status)
├── masterdata/   — Customer and Employee master data CRUD
├── misc/         — Charts, settings, account credentials, T-code help, server config
├── models/       — Customer, Employee, Material, SalesOrder, PurchaseOrder, Seller, DataModel
├── adapters/     — RecyclerView and ExpandableList adapters
└── network/      — VolleySingleton (shared RequestQueue)
```

## Backend

- **PHP** — database connectivity and CRUD operations (`PHP-DBConnectivity/` folder)
- **MySQL** — relational schema (`MySQL DB/` folder)
- **JSON** — data exchange format between app and server

## Tech Stack

| Component | Library |
|---|---|
| HTTP networking | [Volley 1.2.1](https://github.com/google/volley) |
| Charts | [MPAndroidChart v3.1.0](https://github.com/PhilJay/MPAndroidChart) |
| Navigation drawer | [FlowingDrawer 2.0.0](https://github.com/mxn21/FlowingDrawer) |
| Splash screen | [AwesomeSplash v1.0.0](https://github.com/ViksaaSkool/AwesomeSplash) |
| UI components | AndroidX AppCompat 1.7.0, Material Components 1.12.0, RecyclerView 1.3.2 |

## Setup

1. Clone the repository.
2. Open in Android Studio (Hedgehog or newer recommended).
3. Ensure your Gradle JDK is set to **Java 17** (required by AGP 8.x):
   - *File → Project Structure → SDK Location → Gradle JDK*
4. Sync Gradle — dependencies will download automatically.
5. The app points to a PHP/MySQL backend. Update the server base URL in the PHP request strings if you deploy your own backend.

## Modules

| T-Code | Description |
|---|---|
| VA01 / VA02 / VA03 / VA05 | Sales Order Create / Modify / Display / List |
| MM01 / MM02 / MM03 / MM04 / MM12 | Material Create / Modify / Display / List / Stock |
| PP01 / PP02 / PP03 / PP05 / PP10 | Purchase Order Create / Modify / Display / Status / Sellers |
| HR10 | Employee List |
| DD01 | Check & Change Order Status |
| MD01–MD03 | Customer Master Data |
| MD11–MD13 | Employee Master Data |

## Inspiration

Built during an SAP-SD internship as a cheaper ERP alternative for small-to-medium scale industries. The project demonstrates modular ERP design patterns on Android.

## Contributing

Pull requests are welcome! For suggestions or questions about the source code, open an issue or email: jaspreetsinghtuli@gmail.com
