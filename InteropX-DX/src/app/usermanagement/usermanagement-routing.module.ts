import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserManagementComponent } from './usermanagement.component';
import { AdduserComponent } from './addusermanagement/adduser.component';
import { AssignGrpsToUser } from './assigngrpstousers/assigngrpstousers.component';

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                component: UserManagementComponent
            },
            {
                path: 'add',
                component: AdduserComponent
            }, {
                path: '',
                component: AssignGrpsToUser
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserMgmtRoutingModule { }
