import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class DatasetsService {
    url: any;
    deletedid: any;
    constructor(private http: HttpClient, private util: UtilityService) { }

    getListofAllDatasets() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-datasets/dataset/list', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAlldatasets', []))
            );
    }

    createDatasets(json) {
        const params = new URLSearchParams();
        return this.http
            .post(environment.apiBaseUrl + '/ix-datasets/dataset/create/', json, {
                observe: 'response'

            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('createGroupError', []))
            );
    }

    editDatasetRow(json) {
        return this.http
            .put(environment.apiBaseUrl + '/ix-datasets/dataset/update/', json, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('updateDatasetError', []))
            );
    }
    deleteDataset(id: number) {
        this.url = environment.apiBaseUrl;
        this.deletedid = id;
        const dataurl = this.url + '/ix-datasets/dataset/' + this.deletedid;
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
            .pipe(catchError(this.handleError('deletedataset', [])));
    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'getListofAlldatasets') {
                this.util.notify(
                    'Error in fetching list of Data Sets.',
                    'error',
                    error.status
                );
                return result;
            } else if (operation === 'createGroupError') {
                this.util.notify('Error in creating Data Set', 'error', error.status);
                return result;
            } else if (operation === 'updateDatasetError') {
                this.util.notify('Error in Updating Data Set', 'error', error.status);
                return result;
            } else if (operation === 'deletedataset') {
                this.util.notify('Error in Deleting Data Set', 'error', error.status);
                return result;
            }
        };
    }

}