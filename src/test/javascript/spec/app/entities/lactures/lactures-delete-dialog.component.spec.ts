/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdusenseTestModule } from '../../../test.module';
import { LacturesDeleteDialogComponent } from 'app/entities/lactures/lactures-delete-dialog.component';
import { LacturesService } from 'app/entities/lactures/lactures.service';

describe('Component Tests', () => {
    describe('Lactures Management Delete Component', () => {
        let comp: LacturesDeleteDialogComponent;
        let fixture: ComponentFixture<LacturesDeleteDialogComponent>;
        let service: LacturesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [LacturesDeleteDialogComponent]
            })
                .overrideTemplate(LacturesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LacturesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LacturesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
