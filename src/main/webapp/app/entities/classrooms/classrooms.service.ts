import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClassrooms } from 'app/shared/model/classrooms.model';

type EntityResponseType = HttpResponse<IClassrooms>;
type EntityArrayResponseType = HttpResponse<IClassrooms[]>;

@Injectable({ providedIn: 'root' })
export class ClassroomsService {
    public resourceUrl = SERVER_API_URL + 'api/classrooms';

    constructor(protected http: HttpClient) {}

    create(classrooms: IClassrooms): Observable<EntityResponseType> {
        return this.http.post<IClassrooms>(this.resourceUrl, classrooms, { observe: 'response' });
    }

    update(classrooms: IClassrooms): Observable<EntityResponseType> {
        return this.http.put<IClassrooms>(this.resourceUrl, classrooms, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClassrooms>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClassrooms[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
