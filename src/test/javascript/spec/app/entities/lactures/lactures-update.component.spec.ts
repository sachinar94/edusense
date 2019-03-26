/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdusenseTestModule } from '../../../test.module';
import { LacturesUpdateComponent } from 'app/entities/lactures/lactures-update.component';
import { LacturesService } from 'app/entities/lactures/lactures.service';
import { Lactures } from 'app/shared/model/lactures.model';

describe('Component Tests', () => {
    describe('Lactures Management Update Component', () => {
        let comp: LacturesUpdateComponent;
        let fixture: ComponentFixture<LacturesUpdateComponent>;
        let service: LacturesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [LacturesUpdateComponent]
            })
                .overrideTemplate(LacturesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LacturesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LacturesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Lactures(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lactures = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Lactures();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.lactures = entity;
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
