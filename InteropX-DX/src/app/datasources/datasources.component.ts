import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatColumnDef } from '@angular/material';
import { UtilityService } from '../shared/utility.service';
import { AddDatasourcesComponent } from './addnewdatasource/addnewdatasource.component';
import { DatasourceService } from './datasources.service';

@Component({
  selector: 'app-datasource',
  templateUrl: './datasources.component.html',
  styleUrls: ['./datasources.component.scss'],
  providers: [DatasourceService]
})
export class DatasourcesComponent implements OnInit {
  tableOptions: any = {};
  isSecure: string;
  renderDS: any = [];
  securityMethod: any;
  isLoadingResults = false;
  columns = [
    { columnDef: 'dataSourceId', header: 'Data Source Id' },
    { columnDef: 'dataSourceName', header: 'Data Source' },
    { columnDef: 'endPointUrl', header: 'Endpoint' },
    { columnDef: 'connectorType', header: 'Connector' },
    { columnDef: 'securityMethod', header: 'Security' }
  ];
  @Input() dataSourceId: string;
  private _name: string;
  constructor(private dialog: MatDialog, private dataservice: DatasourceService, private util: UtilityService) { }

  ngOnInit() {

    this.tableOptions['tableColumns'] = this.columns;
    this.tableOptions['tableData'] = [];
    this.tableOptions['actions'] = { edit: true, delete: true, viewResults: false };
    this.tableOptions['selectMultipleRows'] = false;
    this.tableOptions['paginator'] = true;
    this.loadDataSources();

  }


  createDataSource() {
    this.isLoadingResults = true;
    const title = 'Add';
    const dialogRef = this.dialog.open(AddDatasourcesComponent, {
      width: '600px',
      data: { title }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result !== '' && result !== undefined) {
        console.log(result);
        const type = result.connectorType;
        if (type === 'FLAT FILE') {
          this.isSecure = 'true';
          const cred = {};
          const credentials = JSON.stringify(cred);
          const sMethod = 'Open';
          const json = {
            connectorType: type,
            credentials: credentials,
            dataSourceName: result.dataSourceName,
            databaseServer: result.db,
            ehrAdminContact: result.ehrAdminContact,
            ehrAdminEmail: result.ehrAdminEmail,
            endPointUrl: result.endPointUrl,
            isSecure: this.isSecure,
            securityMethod: sMethod
          };
          console.log(json);
          this.dataservice.createDatasources(json).subscribe(res => {
            this.isLoadingResults = false;
            this.loadDataSources();
            this.util.notify(
              'Data Source Created Successfully',
              'success',
              'Success'
            );
          });
        } else if (type === 'HL7 FHIR' || type === 'CCDA') {

          const endpoint = result.endPointUrl;
          const auth = result.authenticationType;

          if (auth === 'Secure') {
            this.isSecure = 'true';
            const id = result.clientid;
            const iss = result.Issuerurl;
            const scope = result.Scope;
            const securecred = {
              iss: iss,
              sub: id,
              aud: endpoint,
              scopes: scope
            };
            const securecredentials = JSON.stringify(securecred);
            console.log(securecredentials);
            this.securityMethod = 'Secure';
            const json = {
              connectorType: type,
              credentials: securecredentials,
              dataSourceName: result.dataSourceName,
              databaseServer: '',
              ehrAdminContact: '',
              ehrAdminEmail: '',
              endPointUrl: result.endPointUrl,
              isSecure: this.isSecure,
              securityMethod: this.securityMethod
            };
            console.log(json);
            this.dataservice.createDatasources(json).subscribe(res => {
              this.isLoadingResults = false;
              this.loadDataSources();
              this.util.notify(
                'Data Source Created Successfully',
                'success',
                'Success'
              );
            });
          } else {
            this.isSecure = 'false';
            const cred = {
              scopes: result.openscope
            };
            const credentials = JSON.stringify(cred);
            const sMethod = 'Open';
            const json = {
              connectorType: type,
              credentials: credentials,
              dataSourceName: result.dataSourceName,
              databaseServer: '',
              ehrAdminContact: '',
              ehrAdminEmail: '',
              endPointUrl: result.endPointUrl,
              isSecure: this.isSecure,
              securityMethod: sMethod
            };
            this.dataservice.createDatasources(json).subscribe(res => {
              this.isLoadingResults = false;
              this.loadDataSources();
              this.util.notify(
                'Data Source Created Successfully',
                'success',
                'Success'
              );
            });
          }
        }
      }
    });

  }
  loadDataSources() {
    this.dataservice
      .getListofAllDatasources()
      .subscribe((response: Response) => {
        this.renderDS = response.body;
        const options = {};
        options['tableColumns'] = this.columns;
        options['tableData'] = this.renderDS;
        options['actions'] = { edit: true, delete: true, viewResults: false };
        options['selectMultipleRows'] = false;
        options['paginator'] = true;
        this.tableOptions = options;

      });
  }
  getActionDetails(ev) {
    console.log(ev);
    const id = ev.selectedRow.dataSourceId;
    const updated = ev.selectedRow.lastUpdated;
    const action = ev;
    if (action.actionType === 'Edit') {
      const title = 'Edit';
      const dialogRef = this.dialog.open(AddDatasourcesComponent, {
        width: '600px',
        data: { ev, title }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result !== '' && result !== undefined) {
          console.log(result);
          const type = result.connectorType;
          if (type === 'FLAT FILE') {
            const isSecure = 'false';
            const cred = {};
            const credentials = JSON.stringify(cred);
            const securityMethod = 'Open';
            const json = {
              connectorType: type,
              credentials: credentials,
              dataSourceId: id,
              dataSourceName: result.dataSourceName,
              databaseServer: result.db,
              ehrAdminContact: result.ehrAdminContact,
              ehrAdminEmail: result.ehrAdminEmail,
              endPointUrl: result.endPointUrl,
              isSecure: isSecure,
              securityMethod: securityMethod,
              lastUpdated: updated
            };
            console.log(json);
            this.dataservice.editDatasourceRow(json).subscribe(data => {
              this.loadDataSources();
              this.util.notify(
                'Data Source Updated Successfully',
                'success',
                'Success'
              );
            });
          } else if (type === 'HL7 FHIR' || type === 'CCDA') {
            const auth = result.authenticationType;
            if (auth === 'Secure') {
              const isSecure = 'true';
              const securecred = {
                iss: result.Issuerurl,
                sub: result.clientid,
                aud: result.endPointUrl,
                scopes: result.Scope
              };
              const securecredentials = JSON.stringify(securecred);
              const securityMethod = 'Secure';
              const json = {
                connectorType: type,
                credentials: securecredentials,
                dataSourceId: id,
                dataSourceName: result.dataSourceName,
                databaseServer: '',
                ehrAdminContact: '',
                ehrAdminEmail: '',
                endPointUrl: result.endPointUrl,
                isSecure: isSecure,
                securityMethod: securityMethod,
                lastUpdated: updated
              };
              console.log(json);
              this.dataservice.editDatasourceRow(json).subscribe(data => {
                this.loadDataSources();
                this.util.notify(
                  'Data Source Updated Successfully',
                  'success',
                  'Success'
                );
              });
            } else {
              const isSecure = 'false';
              const openscopes = result[4];
              const securecred = {
                scopes: openscopes
              };
              const securecredentials = JSON.stringify(securecred);
              const securityMethod = 'Open';
              const json = {
                connectorType: type,
                credentials: securecredentials,
                dataSourceId: id,
                dataSourceName: result.dataSourceName,
                databaseServer: '',
                ehrAdminContact: '',
                ehrAdminEmail: '',
                endPointUrl: result.endPointUrl,
                isSecure: isSecure,
                securityMethod: securityMethod,
                lastUpdated: updated
              };
              this.dataservice.editDatasourceRow(json).subscribe(data => {
                this.loadDataSources();
                this.util.notify(
                  'Data Source Updated Successfully',
                  'success',
                  'Success'
                );
              });
            }
          }
        }


      });
    }
    if (action.actionType === 'Delete') {
      const x = ev.selectedRow;
      //var result = confirm("Are you Sure?");
      //if (result) {
      this.tableOptions['tableData'].splice(x, 1)
      this.dataservice.deleteDatasource(x.dataSourceId).subscribe(data => {
        this.loadDataSources();
        this.util.notify(
          'Data Source deleted Successfully',
          'success',
          'Success'
        );
      });
      //}
    }
  }
}
