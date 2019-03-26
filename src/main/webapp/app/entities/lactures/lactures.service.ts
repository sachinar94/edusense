import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILactures } from 'app/shared/model/lactures.model';

type EntityResponseType = HttpResponse<ILactures>;
type EntityArrayResponseType = HttpResponse<ILactures[]>;

@Injectable({ providedIn: 'root' })
export class LacturesService {
    public resourceUrl = SERVER_API_URL + 'api/lactures';

    constructor(protected http: HttpClient) {}

    create(lactures: ILactures): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lactures);
        return this.http
            .post<ILactures>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lactures: ILactures): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lactures);
        return this.http
            .put<ILactures>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILactures>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILactures[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(lactures: ILactures): ILactures {
        const copy: ILactures = Object.assign({}, lactures, {
            startDate: lactures.startDate != null && lactures.startDate.isValid() ? lactures.startDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lactures: ILactures) => {
                lactures.startDate = lactures.startDate != null ? moment(lactures.startDate) : null;
            });
        }
        return res;
    }
}
