import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class DatasourceService {
    url: any;
    deletedid: any;
    editurl: any;
    id: any;

    constructor(private http: HttpClient, private util: UtilityService) { }
    createDatasources(json) {
        const params = new URLSearchParams();
        console.log(json);
        return this.http
            .post(environment.apiBaseUrl + '/ix-datasource/datasource/create/', json, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                if (res.status === 200) {
                    return res;
                } else {
                    this.util.notify(
                        'Error in creating Data Source',
                        'error',
                        `{res.status}`
                    );
                }
            }),
            catchError(this.handleError('createDatasourceError', []))
            );

    }
    getListofAllDatasources() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-datasource/datasource/list', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAlldatasources', []))
            );
    }
    deleteDatasource(id: number) {
        this.url = environment.apiBaseUrl;
        this.deletedid = id;
        const dataurl = this.url + '/ix-datasource/datasource/' + this.deletedid;
        const Type = {
            responseType: 'text',
        }
        const httpOptions = {

            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
            responseType: 'text' as 'text'
        };
        return this.http
            .delete(dataurl, httpOptions)
            .pipe(catchError(this.handleError('deletedatasource', [])));
    }

    editDatasourceRow(json) {
        return this.http
            .put(environment.apiBaseUrl + '/ix-datasource/datasource/update/', json, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('updateDatasourceError', []))
            );
    }
    getdatasourceById(id) {
        return this.http
            .get(environment.apiBaseUrl + '/ix-datasource/datasource/' + id, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getdsById', []))
            );
    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'createDatasourceError') {
                this.util.notify('Error in creating Data Source', 'error', error.status);
                return result;
            } else if (operation === 'getListofAlldatasources') {
                this.util.notify(
                    'Error in fetching list of Data Sources.',
                    'error',
                    error.status
                );
                return result;
            } else if (operation === 'deletedatasource') {
                this.util.notify('Error in deleting  Data Source', 'error', error.status);
            } else if (operation === 'updateDatasourceError') {
                this.util.notify('Error in Updating   Data Source', 'error', error.status);
            } else if (operation === 'getdsById') {
                this.util.notify('Error in Getting the Data Source by Id', 'error', error.status);
            }
        };
    }
}