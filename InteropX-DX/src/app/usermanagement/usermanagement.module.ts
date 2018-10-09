import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '@app/shared/shared.module';
import { UserManagementComponent } from './usermanagement.component';
import { AdduserComponent } from './addusermanagement/adduser.component';
import { AssignGrpsToUser } from './assigngrpstousers/assigngrpstousers.component';
import { UserMgmtRoutingModule } from './usermanagement-routing.module';
import { UserManagementService } from './usermanagement.service';



@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        UserMgmtRoutingModule
    ],
    declarations: [
        UserManagementComponent,
        AdduserComponent,
        AssignGrpsToUser
    ],
    providers: [UserManagementService],
    entryComponents: [AdduserComponent, AssignGrpsToUser],
    exports: [AdduserComponent, AssignGrpsToUser],
})
export class UserMgmtModule { }
