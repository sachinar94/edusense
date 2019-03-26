import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILactures } from 'app/shared/model/lactures.model';
import { AccountService } from 'app/core';
import { LacturesService } from './lactures.service';

@Component({
    selector: 'jhi-lactures',
    templateUrl: './lactures.component.html'
})
export class LacturesComponent implements OnInit, OnDestroy {
    lactures: ILactures[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lacturesService: LacturesService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lacturesService
            .query()
            .pipe(
                filter((res: HttpResponse<ILactures[]>) => res.ok),
                map((res: HttpResponse<ILactures[]>) => res.body)
            )
            .subscribe(
                (res: ILactures[]) => {
                    this.lactures = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLactures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILactures) {
        return item.id;
    }

    registerChangeInLactures() {
        this.eventSubscriber = this.eventManager.subscribe('lacturesListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
