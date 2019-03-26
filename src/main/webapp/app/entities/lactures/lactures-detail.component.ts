import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILactures } from 'app/shared/model/lactures.model';

@Component({
    selector: 'jhi-lactures-detail',
    templateUrl: './lactures-detail.component.html'
})
export class LacturesDetailComponent implements OnInit {
    lactures: ILactures;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lactures }) => {
            this.lactures = lactures;
        });
    }

    previousState() {
        window.history.back();
    }
}
