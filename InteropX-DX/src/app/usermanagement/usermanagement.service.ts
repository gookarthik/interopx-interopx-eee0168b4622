import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class UserManagementService {
    constructor(private http: HttpClient, private util: UtilityService) { }
    getListofAllUserData() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-security-management/admin/api/users', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllUserData', []))
            );
    }
    createNewUser(json) {
        const params = new URLSearchParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
            responseType: 'text' as 'text'
        };
        return this.http
            .post(environment.apiBaseUrl + '/ix-security-management//admin/api/users', json, httpOptions)
            .pipe(
            tap(res => {
                return res;

            }),
            catchError(this.handleError('createNewUser', []))
            );
    }
    deactivatingUser(id) {

        return this.http
            .put(environment.apiBaseUrl + '/ix-security-management//admin/api/users/' + id + '/deactivate', {}, {
                responseType: 'text'
            })
            .pipe(
            tap(res => {

                return res;
            }),
            catchError(this.handleError('deactivateError', []))
            );
    }
    assigngrpstousers(json, id) {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
            responseType: 'text' as 'text'
        };
        return this.http
            .patch(environment.apiBaseUrl + '/ix-security-management//admin/api/users/' + id + '/addgroups', json, httpOptions)
            .pipe(
            tap(res => {
                return res;

            }),
            catchError(this.handleError('assigngrps', []))
            );

    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'getListofAllUserData') {
                this.util.notify('Error in getting list of Matching Data', 'error', error.status);
                return result;
            } if (operation === 'createNewUser') {
                this.util.notify('Error in creating User', 'error', error.status);
                return result;
            } if (operation === 'deactivateError') {
                /* if (error.status === 200) {
                     this.util.notify('deactivated successfully', 'success', error.status);
                     return result;
                 } else {*/
                this.util.notify('Error in Deactivating User', 'error', error.status);
                return result;
            } if (operation === 'assigngrps') {
                this.util.notify('Error in creating User', 'error', error.status);
                return result;
            }
        }
    }
}