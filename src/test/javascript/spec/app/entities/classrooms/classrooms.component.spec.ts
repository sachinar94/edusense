/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdusenseTestModule } from '../../../test.module';
import { ClassroomsComponent } from 'app/entities/classrooms/classrooms.component';
import { ClassroomsService } from 'app/entities/classrooms/classrooms.service';
import { Classrooms } from 'app/shared/model/classrooms.model';

describe('Component Tests', () => {
    describe('Classrooms Management Component', () => {
        let comp: ClassroomsComponent;
        let fixture: ComponentFixture<ClassroomsComponent>;
        let service: ClassroomsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdusenseTestModule],
                declarations: [ClassroomsComponent],
                providers: []
            })
                .overrideTemplate(ClassroomsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClassroomsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassroomsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Classrooms(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.classrooms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
