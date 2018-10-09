import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { UsrMgmt_DATA } from '../shared/entities/mock-data';
import { MatDialog, MatColumnDef } from '@angular/material';
import { AdduserComponent } from './addusermanagement/adduser.component';
import { UserManagementService } from './usermanagement.service';
import { UtilityService } from '../shared/utility.service';
import { AssignGrpsToUser } from './assigngrpstousers/assigngrpstousers.component';



@Component({
  selector: 'app-usermgmt',
  templateUrl: './usermanagement.component.html',
  styleUrls: ['./usermanagement.component.scss']
})
export class UserManagementComponent implements OnInit {
  tableOptions: any = {};
  userObj: any;
  userArray: any = [];
  grpIdlist: any = [];
  displayedColumns = ['select', 'email', 'fname', 'lname', 'status'];
  dataSource = new MatTableDataSource();
  options = [
    { value: 'all', viewValue: 'All' },
    { value: 'email', viewValue: 'Email' },
    { value: 'name', viewValue: 'Name' }
  ];
  @ViewChild(MatPaginator) matpaginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private dialog: MatDialog, private userservice: UserManagementService, private util: UtilityService) {

  }
  ngOnInit() {
    this.loadusers();
  }
  edituser() {
    const title = 'Edit';
    const dialogRef = this.dialog.open(AdduserComponent, {
      width: '600px',
      data: { title }
    });
  }
  createNewUser() {
    const list = [];
    const title = 'Add';
    const dialogRef = this.dialog.open(AdduserComponent, {
      width: '600px',
      data: { title }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result !== '' && result !== undefined) {
        const json = {
          userName: result.uname,
          password: 'password',
          firstName: result.fname,
          lastName: result.lname,
          email: result.email,
          userGroupList: list
        };
        this.userservice.createNewUser(json).subscribe(res => {
          //this.isLoadingResults = false;
          this.loadusers();
          console.log("after load");
          this.util.notify(
            'User Created Successfully',
            'success',
            'Success'
          );
        });
      }
    });

  }
  loadusers() {
    this.userservice.getListofAllUserData().subscribe((response: Response) => {
      this.userObj = response.body;
      this.userArray = [];
      for (let i = 0; i < this.userObj.length; i++) {
        const renderObject = {
          username: this.userObj[i].username,
          email: this.userObj[i].email,
          fname: this.userObj[i].firstName,
          lname: this.userObj[i].lastName,
          status: 'active',
          id: this.userObj[i].userId
        };
        this.userArray.push(renderObject);
      }
      this.dataSource = new MatTableDataSource(this.userArray);
      this.dataSource.paginator = this.matpaginator;
      this.dataSource.sort = this.sort;
    });
  }
  deactivateuser(ev) {
    this.userservice.deactivatingUser(ev.id).subscribe(res => {
      this.loadusers();
      this.util.notify(
        'User De-Activated Successfully',
        'success',
        'Success'
      );
    });

  }
  assigngrpstouser(row) {
    const dialogRef = this.dialog.open(AssignGrpsToUser, {
      width: '600px'
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result !== '' && result !== undefined) {
        this.grpIdlist = [];
        for (let i = 0; i < result.length; i++) {
          const id = {
            groupId: String(result[i].grpId)
          }
          this.grpIdlist.push(id);
        }
        this.userservice.assigngrpstousers(JSON.stringify(this.grpIdlist), row.id).subscribe(res => {
          this.util.notify(
            'Added Groups to User Successfully',
            'success',
            'Success'
          );

        });
      }

    });
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}