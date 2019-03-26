import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassrooms } from 'app/shared/model/classrooms.model';

@Component({
    selector: 'jhi-classrooms-detail',
    templateUrl: './classrooms-detail.component.html'
})
export class ClassroomsDetailComponent implements OnInit {
    classrooms: IClassrooms;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classrooms }) => {
            this.classrooms = classrooms;
        });
    }

    previousState() {
        window.history.back();
    }
}
