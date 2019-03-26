/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdusenseTestModule } from '../../../test.module';
import { ClassroomsUpdateComponent } from 'app/entities/classrooms/classrooms-update.component';
import { ClassroomsService } from 'app/entities/classrooms/classrooms.service';
import { Classrooms } from 'app/shared/model/classrooms.model';

describe('Component Tests', () => {
    describe('Classrooms Management Update Component', () => {
        let comp: ClassroomsUpdateComponent;
        let fixture: ComponentFixture<ClassroomsUpdateComponent>;
        let service: ClassroomsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [ClassroomsUpdateComponent]
            })
                .overrideTemplate(ClassroomsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassroomsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Classrooms(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.classrooms = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Classrooms();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.classrooms = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
