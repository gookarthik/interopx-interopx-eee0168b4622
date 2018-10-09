import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
//import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-add-new-data-set',
  templateUrl: './add-new-data-set.component.html',
  styleUrls: ['./add-new-data-set.component.scss']
})
export class AddNewDataSetComponent implements OnInit {

  dsetForm: FormGroup;
  value = [];
  title: any;
  dsname: any;
  options: any;
  freq: any;
  schtime: any;

  constructor(private dialog: MatDialog,
    public dialogRef: MatDialogRef<AddNewDataSetComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.title = data.title;
  }

  ngOnInit() {
    this.dsetForm = new FormGroup({
      datasetname: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")]),
      options: new FormControl('', [Validators.required]),
      time: new FormControl('', [Validators.required]),
      dbname: new FormControl(''),
      epurl: new FormControl(''),
    });
    if (this.title === 'Edit') {
      // this.dialogdata = this.data.ev.selectedRow.isSecure;
      const dsname = this.data.ev.selectedRow.dataSetName;
      //this.options = this.data.ev.selectedRow.;
      const freq = this.data.ev.selectedRow.scheduleFrequency;
      const schtime = this.data.ev.selectedRow.scheduleTime;
      // const dbname = 
      this.dsetForm.setValue({
        datasetname: dsname,
        options: freq,
        time: schtime,
        dbname: '',
        epurl: ' ',
      });

    }
  }
  onSubmit() {
   
    const datasetObject = {
      dataSetName: this.dsetForm.get('datasetname').value,
      scheduleFrequency: this.dsetForm.get('options').value,
      scheduleTime: this.dsetForm.get('time').value,
      targetDB: this.dsetForm.get('dbname').value,
      endPointUrl: this.dsetForm.get('epurl').value
    }
    //this.value = [dsname, options, time, db, url];
    console.log(datasetObject);
    this.dialogRef.close(datasetObject);
  }

}
