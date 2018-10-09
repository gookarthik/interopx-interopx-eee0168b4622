import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UtilityService } from '../shared/utility.service';
import { environment } from '../../environments/environment';

@Injectable()
export class DataQualityByExtractionService {
    constructor(private http: HttpClient, private util: UtilityService) { }

    getListofAllExtractionData() {
        return this.http
            .get(environment.apiBaseUrl + '/ix-data-quality-scorer/data-quality/distinct/', {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllExtractionData', []))
            );

    }

    getExtractionDataById(Id) {
        return this.http
            .get(environment.apiBaseUrl + '/ix-data-quality-scorer/data-quality/extraction/{etId}?etId=' + Id, {
                observe: 'response'
            })
            .pipe(
            tap(res => {
                return res;
            }),
            catchError(this.handleError('getListofAllExtractionDataById', []))
            );
    }
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
    getdsExtractionsByDsId(id) {
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
            if (operation === 'getListofAllExtractionData') {
                this.util.notify('Error in getting list of Extractions', 'error', error.status);
                return result;
            } if (operation === 'getListofAllExtractionDataById') {
                this.util.notify('Error in getting Extraction By Id', 'error', error.status);
                return result;
            } if (operation === 'getListofAllDataQualityByDsData') {
                this.util.notify('Error in getting list of Extractions', 'error', error.status);
                return result;
            } if (operation === 'getExtractionsByDsId') {
                this.util.notify('Error in getting Extractions By Datasource Id', 'error', error.status);
                return result;
            }
        }
    }
}