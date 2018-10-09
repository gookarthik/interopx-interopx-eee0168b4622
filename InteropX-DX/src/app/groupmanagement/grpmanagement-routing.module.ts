import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GrpManagementComponent } from './grpmanagement.component';
import { EditGrpComponent } from './editgrpmanagement/editgrpmanagement.component';
import { AddMemebersToGroup } from './addmemberstogrp/addmemberstogrp.component';
import { AddUsersToGroup } from './adduserstogroup/adduserstogroup.component';

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                component: GrpManagementComponent
            },
            {
                path: 'edit',
                component: EditGrpComponent
            }, {
                path: '',
                component: AddMemebersToGroup
            }, {
                path: '',
                component: AddUsersToGroup
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class GrpMgmtRoutingModule { }
