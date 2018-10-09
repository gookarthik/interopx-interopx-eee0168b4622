import { Component, OnInit } from '@angular/core';
import { EXTRACTED_ALLERGY_DATA, EXTRACTED_CONDITION_DATA, EXTRACTED_MEDICATION_DATA, EXTRACTED_OBSERVATION_DATA } from '@app/shared/entities/mock-data';
@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {
  allergyTableOptions: any = {};
  conditionTableOptions: any = {};
  medicationTableOptions: any = {};
  observationTableOptions: any = {};
  constructor() { }

  ngOnInit() {
    // Allergy Table
    this.allergyTableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Source Name' },
      { columnDef: 'allergyId', header: 'Allergy Intolerance Id' },
      { columnDef: 'status', header: 'Status' },
      { columnDef: 'code', header: 'Code' },
      { columnDef: 'display', header: 'Display' },
      { columnDef: 'patient', header: 'Patient' }
    ];
    this.allergyTableOptions['tableData'] = EXTRACTED_ALLERGY_DATA;
    this.allergyTableOptions['selectMultipleRows'] = false;

    // Conditions Table
    this.conditionTableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Source Name' },
      { columnDef: 'allergyId', header: 'Condition Id' },
      { columnDef: 'status', header: 'Clinical Status' },
      { columnDef: 'code', header: 'Code' },
      { columnDef: 'display', header: 'Display' },
      { columnDef: 'patient', header: 'Patient' }
    ];
    this.conditionTableOptions['tableData'] = EXTRACTED_CONDITION_DATA;
    this.conditionTableOptions['selectMultipleRows'] = false;

    // Medications Table
    this.medicationTableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Source Name' },
      { columnDef: 'medicationId', header: 'Medication Id' },
      { columnDef: 'status', header: 'Status' },
      { columnDef: 'display', header: 'Medication' },
      { columnDef: 'patient', header: 'Patient' }
    ];
    this.medicationTableOptions['tableData'] = EXTRACTED_MEDICATION_DATA;
    this.medicationTableOptions['selectMultipleRows'] = false;

    // Observations Table
    this.observationTableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Source Name' },
      { columnDef: 'observationId', header: 'Observation Id' },
      { columnDef: 'category', header: 'Category' },
      { columnDef: 'code', header: 'Code' },
      { columnDef: 'display', header: 'Display' },
      { columnDef: 'value', header: 'Value' },
      { columnDef: 'patient', header: 'Patient' }
    ];
    this.observationTableOptions['tableData'] = EXTRACTED_OBSERVATION_DATA;
    this.observationTableOptions['selectMultipleRows'] = false;
  }

}
