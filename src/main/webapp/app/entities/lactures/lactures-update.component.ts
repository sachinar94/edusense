import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ILactures } from 'app/shared/model/lactures.model';
import { LacturesService } from './lactures.service';

@Component({
    selector: 'jhi-lactures-update',
    templateUrl: './lactures-update.component.html'
})
export class LacturesUpdateComponent implements OnInit {
    lactures: ILactures;
    isSaving: boolean;
    startDateDp: any;

    constructor(protected lacturesService: LacturesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lactures }) => {
            this.lactures = lactures;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lactures.id !== undefined) {
            this.subscribeToSaveResponse(this.lacturesService.update(this.lactures));
        } else {
            this.subscribeToSaveResponse(this.lacturesService.create(this.lactures));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILactures>>) {
        result.subscribe((res: HttpResponse<ILactures>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
