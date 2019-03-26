import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Classrooms } from 'app/shared/model/classrooms.model';
import { ClassroomsService } from './classrooms.service';
import { ClassroomsComponent } from './classrooms.component';
import { ClassroomsDetailComponent } from './classrooms-detail.component';
import { ClassroomsUpdateComponent } from './classrooms-update.component';
import { ClassroomsDeletePopupComponent } from './classrooms-delete-dialog.component';
import { IClassrooms } from 'app/shared/model/classrooms.model';

@Injectable({ providedIn: 'root' })
export class ClassroomsResolve implements Resolve<IClassrooms> {
    constructor(private service: ClassroomsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClassrooms> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Classrooms>) => response.ok),
                map((classrooms: HttpResponse<Classrooms>) => classrooms.body)
            );
        }
        return of(new Classrooms());
    }
}

export const classroomsRoute: Routes = [
    {
        path: '',
        component: ClassroomsComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Classrooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClassroomsDetailComponent,
        resolve: {
            classrooms: ClassroomsResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Classrooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClassroomsUpdateComponent,
        resolve: {
            classrooms: ClassroomsResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Classrooms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClassroomsUpdateComponent,
        resolve: {
            classrooms: ClassroomsResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Classrooms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classroomsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClassroomsDeletePopupComponent,
        resolve: {
            classrooms: ClassroomsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Classrooms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
