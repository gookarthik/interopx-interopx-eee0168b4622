import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-newdatasource',
  templateUrl: './addnewdatasource.component.html',
  styleUrls: ['./addnewdatasource.component.scss']
})
export class AddDatasourcesComponent implements OnInit {

  dsTypeForm: FormGroup;
  type: any;
  flatfileForm: FormGroup;
  //flatfileype: boolean;
  HL7CCDAForm: FormGroup;
  //hl7ccda: boolean;
  secureForm: FormGroup;
  //securetype: boolean;
  openForm: FormGroup;
  //opentype: boolean;
  title: string;
  dialogdata: Object;
  dsname: string;
  epoint: string;
  connecttype: string;
  iss: string;
  ehremail: any;
  ehrcontact: any;
  dbserver: any;
  isSecure: any;
  enablelinks: Object;

  connectorTypes = [
    { value: 'HL7 FHIR', viewValue: 'HL7 FHIR' },
    { value: 'CCDA', viewValue: 'CCDA' },
    { value: 'FLAT FILE', viewValue: 'FLAT FILE' }
  ];
  ehrdbs = [
    { value: 'Postgres', viewValue: 'Postgres' },
    { value: 'mysql', viewValue: 'MYSQL' }
  ]
  securities = [
    { value: 'Open', viewValue: 'Open' },
    { value: 'Secure', viewValue: 'Secure' }
  ];
  scopes = [
    { value: 'system/*.read', viewValue: 'System/*.read' },
    { value: 'User/*.read', viewValue: 'User/*.read' }
  ];


  constructor(private dialog: MatDialog,
    public dialogRef: MatDialogRef<AddDatasourcesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.title = data.title;
  }

  ngOnInit() {
    this.dsTypeForm = new FormGroup({
      connectortype: new FormControl('')
    });
    this.flatfileForm = new FormGroup({
      dsName: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")]),
      ehremail: new FormControl('', [Validators.required, Validators.pattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")]),
      ehrdb: new FormControl('', [Validators.required]),
      endPoint: new FormControl('', [Validators.required]),
      contact: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")])
    });
    this.HL7CCDAForm = new FormGroup({
      securedsName: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ]*$")]),
      endpointurl: new FormControl('', [Validators.required]),
      authentication: new FormControl('', [Validators.required])
    });
    this.secureForm = new FormGroup({
      clientid: new FormControl('', [Validators.required]),
      secureiss: new FormControl('', [Validators.required]),
      scope: new FormControl('', [Validators.required])
    });
    this.openForm = new FormGroup({
      userscope: new FormControl('', [Validators.required])
    });
    this.enablelinks = {
      flatfiletype: false,
      hl7ccda: false,
      securetype: false,
      opentype: false
    }

    if (this.title === 'Edit') {
      this.dialogdata = this.data.ev.selectedRow;
      this.dsname = this.data.ev.selectedRow.dataSourceName;
      this.epoint = this.data.ev.selectedRow.endPointUrl;
      this.connecttype = this.data.ev.selectedRow.connectorType;
      this.ehremail = this.data.ev.selectedRow.ehrAdminEmail;
      this.dbserver = this.data.ev.selectedRow.databaseServer;
      this.ehrcontact = this.data.ev.selectedRow.ehrAdminContact;
      this.isSecure = this.data.ev.selectedRow.isSecure;

      if (this.connecttype === 'FLAT FILE') {
        this.enablelinks['flatfiletype'] = true;
        this.enablelinks['opentype'] = false;
        this.enablelinks['securetype'] = false;
        this.enablelinks['hl7ccda'] = false;
        this.dsTypeForm.setValue({
          connectortype: this.connecttype
        });
        this.flatfileForm.setValue({
          dsName: this.dsname,
          ehremail: this.ehremail,
          ehrdb: this.dbserver,
          endPoint: this.epoint,
          contact: this.ehrcontact
        });
      }
      if (this.connecttype === 'HL7 FHIR' || this.connecttype === 'CCDA') {
        this.iss = this.data.ev.selectedRow.credentials;
        const json = JSON.parse(this.iss);
        const issurl = json.iss;
        const clientid = json.sub;
        const scopelist = json.scopes;
        this.enablelinks['flatfiletype'] = false;
        this.enablelinks['hl7ccda'] = true;
        this.dsTypeForm.setValue({
          connectortype: this.connecttype
        });
        const auth = 'Secure';

        if (this.isSecure === true) {
          this.enablelinks['securetype'] = true;
          this.enablelinks['opentype'] = false;
          this.HL7CCDAForm.setValue({
            securedsName: this.dsname,
            endpointurl: this.epoint,
            authentication: auth
          });
          this.secureForm.setValue({
            clientid: clientid,
            secureiss: issurl,
            scope: scopelist
          });

        } else {
          this.iss = this.data.ev.selectedRow.credentials;
          const openjson = JSON.parse(this.iss);
          const scopelist = openjson.scopes;
          const auth = 'Open';
          this.enablelinks['securetype'] = false;
          this.enablelinks['opentype'] = true;
          this.HL7CCDAForm.setValue({
            securedsName: this.dsname,
            endpointurl: this.epoint,
            authentication: auth
          });
          this.openForm.setValue({
            userscope: scopelist
          });
        }
      }
    }
  }
  ontypeSelect() {
    if (this.dsTypeForm.get('connectortype').value === 'FLAT FILE') {
      this.enablelinks['flatfiletype'] = true;
      this.enablelinks['securetype'] = false;
      this.enablelinks['opentype'] = false;
      this.enablelinks['hl7ccda'] = false;
    } else {
      this.enablelinks['flatfiletype'] = false;
      this.enablelinks['hl7ccda'] = true;
    }
  }
  onsecuritySelect() {
    if (this.HL7CCDAForm.get('authentication').value === 'Secure') {
      this.enablelinks['securetype'] = true;
      this.enablelinks['opentype'] = false;
    } else {
      this.enablelinks['securetype'] = false;
      this.enablelinks['opentype'] = true;

    }
  }
  saveopenType() {
    if (this.dsTypeForm.get('connectortype').value === 'FLAT FILE') {
      const flatfileObject = {
        connectorType: this.dsTypeForm.get('connectortype').value,
        dataSourceName: this.flatfileForm.get('dsName').value,
        ehrAdminEmail: this.flatfileForm.get('ehremail').value,
        db: this.flatfileForm.get('ehrdb').value,
        endPointUrl: this.flatfileForm.get('endPoint').value,
        ehrAdminContact: this.flatfileForm.get('contact').value
      }
      console.log(flatfileObject);
      this.dialogRef.close(flatfileObject);
    }
    if (this.dsTypeForm.get('connectortype').value === 'HL7 FHIR' || this.dsTypeForm.get('connectortype').value === 'CCDA') {
      const auth = this.HL7CCDAForm.get('authentication').value;
      if (auth === 'Secure') {
        const HL7Object = {
          connectorType: this.dsTypeForm.get('connectortype').value,
          dataSourceName: this.HL7CCDAForm.get('securedsName').value,
          endPointUrl: this.HL7CCDAForm.get('endpointurl').value,
          authenticationType: this.HL7CCDAForm.get('authentication').value,
          clientid: this.secureForm.get('clientid').value,
          Issuerurl: this.secureForm.get('secureiss').value,
          Scope: this.secureForm.get('scope').value
        }
        console.log(HL7Object);
        this.dialogRef.close(HL7Object);
      } else {
        const HL7Object = {
          connectorType: this.dsTypeForm.get('connectortype').value,
          dataSourceName: this.HL7CCDAForm.get('securedsName').value,
          endPointUrl: this.HL7CCDAForm.get('endpointurl').value,
          authenticationType: this.HL7CCDAForm.get('authentication').value,
          openscope: this.openForm.get('userscope').value
        }
        console.log(HL7Object);
        this.dialogRef.close(HL7Object);

      }


    }
  }
}
