import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lactures } from 'app/shared/model/lactures.model';
import { LacturesService } from './lactures.service';
import { LacturesComponent } from './lactures.component';
import { LacturesDetailComponent } from './lactures-detail.component';
import { LacturesUpdateComponent } from './lactures-update.component';
import { LacturesDeletePopupComponent } from './lactures-delete-dialog.component';
import { ILactures } from 'app/shared/model/lactures.model';

@Injectable({ providedIn: 'root' })
export class LacturesResolve implements Resolve<ILactures> {
    constructor(private service: LacturesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILactures> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lactures>) => response.ok),
                map((lactures: HttpResponse<Lactures>) => lactures.body)
            );
        }
        return of(new Lactures());
    }
}

export const lacturesRoute: Routes = [
    {
        path: '',
        component: LacturesComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Lactures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LacturesDetailComponent,
        resolve: {
            lactures: LacturesResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Lactures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LacturesUpdateComponent,
        resolve: {
            lactures: LacturesResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Lactures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LacturesUpdateComponent,
        resolve: {
            lactures: LacturesResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Lactures'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lacturesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LacturesDeletePopupComponent,
        resolve: {
            lactures: LacturesResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_TEACHER'],
            pageTitle: 'Lactures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
