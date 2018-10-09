import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class DataQualityByDatasourceService {
    constructor(private http: HttpClient, private util: UtilityService) { }

    getListofAllDataQualityByDsData() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-data-quality-scorer/data-quality/distinct/datasource', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllDataQualityByDsData', []))
            );

    }
    getExtractionsByDsId(id) {
        return this.http
            .get(environment.apiBaseUrl + '/ix-data-quality-scorer/data-quality/datasource/{dsId}?dsId=' + id, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getExtractionsByDsId', []))
            );

    }
    private handleError(operation, result) {
        return (error: any) => {
            if (operation === 'getListofAllDataQualityByDsData') {
                this.util.notify('Error in getting list of Extractions', 'error', error.status);
                return result;
            } if (operation === 'getExtractionsByDsId') {
                this.util.notify('Error in getting Extractions By Datasource Id', 'error', error.status);
                return result;
            }
        }
    }
}