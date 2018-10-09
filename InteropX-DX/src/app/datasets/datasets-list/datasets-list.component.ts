import { Component, OnInit } from '@angular/core';
import { DATASET_DATA } from '@app/shared/entities/mock-data';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-datasets-list',
  templateUrl: './datasets-list.component.html',
  styleUrls: ['./datasets-list.component.scss']
})
export class DatasetsListComponent implements OnInit {
  tableOptions: any = {};
  constructor(private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.tableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Set Name' },
      { columnDef: 'schedule', header: 'Scheduling Frequency' },
      { columnDef: 'lastExtracted', header: 'Last Extracted Date/Time' },
      { columnDef: 'target', header: 'Target Database' },
    ];
    this.tableOptions['tableData'] = DATASET_DATA;
    this.tableOptions['actions'] = { edit: false, delete: false, extract: true, viewResults: true };
    this.tableOptions['selectMultipleRows'] = false;
  }

  getRowDetails(details) {
    if (details.actionType === 'View') {
      this.router.navigate(['/datasets/details', details.selectedRow.dsId]);
    }
  }

}
