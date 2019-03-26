import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILactures } from 'app/shared/model/lactures.model';
import { LacturesService } from './lactures.service';

@Component({
    selector: 'jhi-lactures-delete-dialog',
    templateUrl: './lactures-delete-dialog.component.html'
})
export class LacturesDeleteDialogComponent {
    lactures: ILactures;

    constructor(protected lacturesService: LacturesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lacturesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lacturesListModification',
                content: 'Deleted an lactures'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lactures-delete-popup',
    template: ''
})
export class LacturesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lactures }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LacturesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lactures = lactures;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lactures', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lactures', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
