/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdusenseTestModule } from '../../../test.module';
import { ClassroomsDeleteDialogComponent } from 'app/entities/classrooms/classrooms-delete-dialog.component';
import { ClassroomsService } from 'app/entities/classrooms/classrooms.service';

describe('Component Tests', () => {
    describe('Classrooms Management Delete Component', () => {
        let comp: ClassroomsDeleteDialogComponent;
        let fixture: ComponentFixture<ClassroomsDeleteDialogComponent>;
        let service: ClassroomsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [ClassroomsDeleteDialogComponent]
            })
                .overrideTemplate(ClassroomsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClassroomsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomsService);
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
