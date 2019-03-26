import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClassrooms } from 'app/shared/model/classrooms.model';
import { AccountService } from 'app/core';
import { ClassroomsService } from './classrooms.service';

@Component({
    selector: 'jhi-classrooms',
    templateUrl: './classrooms.component.html'
})
export class ClassroomsComponent implements OnInit, OnDestroy {
    classrooms: IClassrooms[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected classroomsService: ClassroomsService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.classroomsService
            .query()
            .pipe(
                filter((res: HttpResponse<IClassrooms[]>) => res.ok),
                map((res: HttpResponse<IClassrooms[]>) => res.body)
            )
            .subscribe(
                (res: IClassrooms[]) => {
                    this.classrooms = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInClassrooms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IClassrooms) {
        return item.id;
    }

    registerChangeInClassrooms() {
        this.eventSubscriber = this.eventManager.subscribe('classroomsListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
