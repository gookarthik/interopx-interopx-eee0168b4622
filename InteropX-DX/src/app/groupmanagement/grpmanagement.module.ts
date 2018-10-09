import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GrpManagementComponent } from './grpmanagement.component';
import { SharedModule } from '@app/shared/shared.module';
import { EditGrpComponent } from './editgrpmanagement/editgrpmanagement.component';
import { AddMemebersToGroup } from './addmemberstogrp/addmemberstogrp.component';
import { GrpMgmtRoutingModule } from './grpmanagement-routing.module';
import { AddUsersToGroup } from './adduserstogroup/adduserstogroup.component';
import { GrpmanagementService } from '@app/groupmanagement/grpmanagement.service';
@NgModule({
    imports: [
        CommonModule,
        GrpMgmtRoutingModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        GrpManagementComponent,
        EditGrpComponent,
        AddMemebersToGroup,
        AddUsersToGroup
    ],
    providers: [GrpmanagementService],
    entryComponents: [EditGrpComponent, AddMemebersToGroup, AddUsersToGroup],
    exports: [EditGrpComponent, AddMemebersToGroup, AddUsersToGroup],
})
export class GroupMgmtModule { }
