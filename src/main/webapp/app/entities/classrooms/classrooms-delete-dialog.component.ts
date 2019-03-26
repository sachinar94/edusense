import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassrooms } from 'app/shared/model/classrooms.model';
import { ClassroomsService } from './classrooms.service';

@Component({
    selector: 'jhi-classrooms-delete-dialog',
    templateUrl: './classrooms-delete-dialog.component.html'
})
export class ClassroomsDeleteDialogComponent {
    classrooms: IClassrooms;

    constructor(
        protected classroomsService: ClassroomsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classroomsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'classroomsListModification',
                content: 'Deleted an classrooms'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-classrooms-delete-popup',
    template: ''
})
export class ClassroomsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ classrooms }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClassroomsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.classrooms = classrooms;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/classrooms', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/classrooms', { outlets: { popup: null } }]);
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
