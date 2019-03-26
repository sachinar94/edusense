import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IClassrooms } from 'app/shared/model/classrooms.model';
import { ClassroomsService } from './classrooms.service';

@Component({
    selector: 'jhi-classrooms-update',
    templateUrl: './classrooms-update.component.html'
})
export class ClassroomsUpdateComponent implements OnInit {
    classrooms: IClassrooms;
    isSaving: boolean;

    constructor(protected classroomsService: ClassroomsService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ classrooms }) => {
            this.classrooms = classrooms;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.classrooms.id !== undefined) {
            this.subscribeToSaveResponse(this.classroomsService.update(this.classrooms));
        } else {
            this.subscribeToSaveResponse(this.classroomsService.create(this.classrooms));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassrooms>>) {
        result.subscribe((res: HttpResponse<IClassrooms>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
