import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { AddNewDataSetComponent } from './add-new-data-set/add-new-data-set.component';
import { UtilityService } from '../shared/utility.service';
import { DatasetsService } from './datasets.service';
import { DatasetsListComponent } from '@app/datasets/datasets-list/datasets-list.component';
import { DatasourceService } from '../datasources/datasources.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-datasets',
  templateUrl: './datasets.component.html',
  styleUrls: ['./datasets.component.scss']
})
export class DatasetsComponent implements OnInit {
  tableOptions: any = {};
  dsnames: any;
  selectedDs = [];
  columns = [
    { columnDef: 'dataSourceId', header: 'Id' },
    { columnDef: 'dataSourceName', header: 'DataSource' },
    { columnDef: 'endPointUrl', header: 'Endpoint URL' },
    { columnDef: 'connectorType', header: 'Connector' },
    { columnDef: 'securityMethod', header: 'Security' }
  ]
  constructor(public dialog: MatDialog, private util: UtilityService, private datasetService: DatasetsService, private dataservice: DatasourceService, private router: Router, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.tableOptions['tableColumns'] = this.columns;
    this.tableOptions['tableData'] = [];
    this.tableOptions['selectMultipleRows'] = true;
    this.tableOptions['paginator'] = true;
    this.loadPatients();
  }
  loadPatients() {
    this.dataservice
      .getListofAllDatasources()
      .subscribe((response: Response) => {
        this.dsnames = response.body;
        const options = {};
        options['tableColumns'] = this.columns;
        options['tableData'] = this.dsnames;
        options['selectMultipleRows'] = true;
        this.tableOptions['paginator'] = true;
        this.tableOptions = options;
      });
  }

  createDataSet() {
    const title = 'Add New';
    console.log(this.selectedDs);
    if (this.selectedDs.length !== 0) {
      const dialogRef = this.dialog.open(AddNewDataSetComponent, {
        width: '600px',
        data: { title }
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        const dsetname = result.dataSetName;
        const schedulefreq = result.scheduleFrequency;
        const scheduletime = result.scheduleTime
        this.createPanel(dsetname, schedulefreq, scheduletime, this.selectedDs);
      });
    }
    else {
      this.util.notify(
        'Select atleast one Data Source to create Data Set',
        'warn',
        'Warning'
      );
    }
  }
  createPanel(dsetname, schedulefreq, scheduletime, selectedDs) {
    const members = [];
    if (selectedDs.length !== 0) {
      const json = {
        dataSetName: dsetname,
        data_sources: selectedDs,
        scheduleFrequency: schedulefreq,
        scheduleTime: scheduletime
      };
      console.log(json);
      this.datasetService.createDatasets(json).subscribe(res => {
        this.util.notify('Data Set Created Successfully', 'success', 'Success');
        this.router.navigate(['/datasets'])

      });
    } else {
      this.util.notify(
        'Select atleast one Data Source to create Data Set',
        'warn',
        'Warning'
      );
    }
  }
  getSelectionDetails(ev) {
    this.selectedDs = ev;
    //if (ev.length! = 0) {
    // this.datasetService.createGroups(ev).subscribe(res =>{
    //  console.log(res);
    //});
    //}
  }

}
