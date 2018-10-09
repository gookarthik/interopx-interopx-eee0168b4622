import { Component, OnInit } from '@angular/core';
import { SAMPLE_DATA } from '@app/shared/entities/mock-data';
import { UtilityService } from '@app/shared/utility.service';

@Component({
  selector: 'app-sample',
  templateUrl: './sample.component.html',
  styleUrls: ['./sample.component.scss']
})
export class SampleComponent implements OnInit {
  tableOptions: any = {};
  constructor(private util: UtilityService) { }

  ngOnInit() {
    this.tableOptions['tableColumns'] = [
      { columnDef: 'name', header: 'Name' },
      { columnDef: 'age', header: 'Age' },
      { columnDef: 'gender', header: 'Gender' }
    ];
    this.tableOptions['tableData'] = SAMPLE_DATA;
    this.tableOptions['actions'] = { edit: true, delete: true, extract: false };
    this.tableOptions['selectMultipleRows'] = true;
  }

  notify(type: any) {
    this.util.notify(
      'This is a Sample text',
      type,
      'Sample'
    );
  }

}
