export interface IClassrooms {
    id?: number;
    name?: string;
    lactureHall?: string;
}

export class Classrooms implements IClassrooms {
    constructor(public id?: number, public name?: string, public lactureHall?: string) {}
}
