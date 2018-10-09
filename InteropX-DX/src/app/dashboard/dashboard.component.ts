import { Component, OnInit } from '@angular/core';
import { DASHBOARD_DATA } from '@app/shared/entities/mock-data';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  tableOptions: any = {};
  //line
  public lineChartData: Array<any> = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'User1' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'User2' },
  ];
  public lineChartLabels: Array<any> = ['1-06-2018', '2-06-2018', '3-06-2018', '4-06-2018', '5-06-2018', '6-06-2018', '7-06-2018'];
  public lineChartType: string = 'line';
  public pieChartType: string = 'pie';
  public pieChartLabels: string[] = ['Download Sales', 'In-Store Sales', 'Mail Sales'];
  public pieChartData: number[] = [300, 500, 100];
  constructor() { }

  ngOnInit() {
    this.tableOptions['tableColumns'] = [
      { columnDef: 'dsName', header: 'Data Set' },
      { columnDef: 'user', header: 'User' },
      { columnDef: 'tar', header: 'Target' },
      { columnDef: 'record', header: 'No of Records' },
      { columnDef: 'status', header: 'Status' }
    ];
    this.tableOptions['tableData'] = DASHBOARD_DATA;
    //this.tableOptions['actions'] = { edit: true, delete: true, extract: false };
    this.tableOptions['selectMultipleRows'] = false;
  }

}
