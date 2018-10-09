import { MatDialog } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { DATASET_DATASOURCE_DATA, DATASET_PATIENT_DATA } from '@app/shared/entities/mock-data';
import { PatientDetailsComponent } from '@app/datasets/patient-details/patient-details.component';


@Component({
  selector: 'app-dataset-details',
  templateUrl: './dataset-details.component.html',
  styleUrls: ['./dataset-details.component.scss']
})
export class DatasetDetailsComponent implements OnInit {
  tableOptions: any = {};
  patientTableOptions: any = {};
  dataSourceSelected = false;
  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    this.tableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Set Name' }
    ];
    this.tableOptions['tableData'] = DATASET_DATASOURCE_DATA;
    this.tableOptions['selectMultipleRows'] = false;

    this.patientTableOptions['tableColumns'] = [
      { columnDef: 'patientId', header: 'Patient Id' },
      { columnDef: 'fName', header: 'First Name' },
      { columnDef: 'lName', header: 'Last Name' },
      { columnDef: 'gender', header: 'Gender' },
      { columnDef: 'dob', header: 'Date of Birth' }
    ];
    this.patientTableOptions['tableData'] = DATASET_PATIENT_DATA;
    this.patientTableOptions['selectMultipleRows'] = false;
  }

  getDataSourceRow(dataSource) {
    this.dataSourceSelected = true;
  }

  getPatientRow(patient) {
    const dialogRef = this.dialog.open(PatientDetailsComponent, {
      width: '90%'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
