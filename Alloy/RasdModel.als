//Italian fiscal code
sig FiscalCode {}

//Object of a service offered by a third part
abstract sig ServiceObject{}

//User of the SafeStreets' System
//He/she must be verified by a third part
sig User extends ServiceObject {
	fiscalCode: one FiscalCode
}

//Password of the registration in SafeStreets' System
sig Password {}

//Photo of a violation
sig Photo {}
//Time and date
sig Timestamp {}

//Specific position in the world, identified by some coordinates
sig Position extends ServiceObject {}
//House code
sig HouseCode{}
//Street
sig Street{}
//City
sig City{}
//Specific	place in the world, identified by a city, an house code and a street or by a position
sig Place extends ServiceObject {
	city: one City,
	houseCode: one HouseCode,
	street: one Street,
	position: one Position
}

//Type of violation
abstract sig ViolationType {}
//Type of violation referring to a parking violation
sig ParkingViolationType extends ViolationType {}
//Type of violation referring to an accident
sig AccidentViolationType extends ViolationType {}

//License plate of a vehicle
sig LicensePlate extends ServiceObject {}
//Vehicle
sig Vehicle {
	licensePlate: one LicensePlate
}
//Report referring to a violation
abstract sig Report {
	//Timestamp of when the violation was seen, if it is empty it means that it is equal to timeOfReport
	timeOfWatchedViolation: lone Timestamp,
	//Timestamp of when the report was made
	timeOfReport: one Timestamp,
	//Vehicle that has done the violation
	vehicle: one Vehicle,
	//Place where the violation was seen
	place: one Place,
	violation: one Violation
}

//Report made by an user
sig ReportFromUser extends Report {
	user: one User,
	//Photos made by the user
	photo: some Photo
}

//report made by a Municipality
sig ReportFromMunicipality extends Report {
	
}

//Real violation that happened in the world
sig Violation {
	violationType: one ViolationType
}

//Municipality
sig Municipality {
	//Place where the Municipality resides
	place: one Place,
	//TrafficTickets made by the Municipality
	trafficTicket: set TrafficTicket,
	//Reports taken by the Municipality from Safestreets
	takenReportFromUser: set ReportFromUser
}

//Registration to the SafeStreets' System
abstract sig Registration {
	password: one Password
}

//Registration of an user
sig UserRegistration {
	user: one User
}

//Registration of a Municipality
sig MunicipalityRegistration {
	municipality: one Municipality
}

//SafeStreets' System
one sig SafeStreets {
	suggestion: set Suggestion,
	statistic: set Statistic,
	reportedViolation: set ReportFromUser,
	takenReportFromMunicipality: set ReportFromMunicipality
}

abstract sig ThirdPart{}

one sig IdentityVerifier extends ThirdPart {}

one sig RecognitionPlateSystem extends ThirdPart {}

one sig MapService extends ThirdPart {}

//This means that the third part has done something on serviceObject
sig Service {
	serviceObject: one ServiceObject,
	thirdPart: one ThirdPart
}

sig TrafficTicket {
	refersToReport: one Report
}

abstract sig Statistic {
	forUser: lone User, //means that the stastic is for an user
	forMunicipality: lone Municipality //means that the stastic is for an Municipality
} {
	#forUser=1 or #forMunicipality=1
}

sig StreetStatistic extends Statistic{}
sig EffectivenessOfServiceStatistic extends Statistic{}
sig VehicleStatistic extends Statistic{}
sig CommonViolationStatistic extends Statistic{}


sig Suggestion {
	place: one Place, //place where the suggestion refers
	forMunicipality: one Municipality
}


--Facts:--

fact eachUserHasAnUniqueFiscalCode {
	all disj u1, u2 : User | u1.fiscalCode != u2.fiscalCode
}

fact eachFiscalCodeToAUser {
	all f: FiscalCode | one u: User | u.fiscalCode=f
}


fact eachMunicipalityIsInADifferentCity {
	all disj m1, m2: Municipality | m1.place.city != m2.place.city
}


fact eachPlaceHasDifferentPosition {
	all disj p1, p2: Place | p1.position!=p2.position and (p1.city!=p2.city or p1.houseCode!=p2.houseCode or
		p1.street!=p2.street)
}


fact licencePlateIsUnique {
	all disj v1, v2: Vehicle | v1.licensePlate!=v2.licensePlate
}


fact eachLicencePlateToAVehicle {
	all l: LicensePlate | one v: Vehicle | v.licensePlate=l
}


fact trafficTicketMadeByOneMunicipality {
	all disj m1, m2: Municipality | m1.trafficTicket & m2.trafficTicket = none
}


fact eachPhotoDateTimeVehicleToAReport {
	all p: Photo | (one r: ReportFromUser | p in r.photo)
	all t: Timestamp | (one r: Report |  r.timeOfWatchedViolation=t or r.timeOfReport=t)
	all ve: Vehicle | (one r: Report |  r.vehicle=ve)
}


fact eachViolationTypeToAViolation {
	all vT: ViolationType | one v: Violation | v.violationType=vT
}


fact eachCityHouseCodeStreetCityPositionToAPlace {
	all c: City | one p : Place | p.city=c	
	all hC: HouseCode | one p : Place | p.houseCode=hC
	all s: Street | one p : Place | p.street=s
	all pos: Position | one p : Place | p.position=pos
}




fact eachTrafficTicketWasMadeByAMunicipality {
	all t: TrafficTicket | one m: Municipality | t in m.trafficTicket
}


fact eachPlaceAndPositionAndTrafficPlateInAReportWereIdentified {
	all p: Place | all vR: Report | p=vR.place implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all p: Position | all vR: Report | p=vR.place.position implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all tP: LicensePlate | all vR: Report | tP=vR.vehicle.licensePlate implies 
		one s: Service | one rPS: RecognitionPlateSystem | s.thirdPart=rPS and s.serviceObject=tP
}

fact eachPasswordToARegistration {
	all p: Password | one r: Registration | r.password=p
}

fact eachStatisticAndSuggestionToSafeStreets {
	all stat: Statistic | one safeS: SafeStreets | stat in safeS.statistic
	all sugg: Suggestion | one safeS: SafeStreets | sugg in safeS.suggestion
}

fact trafficTicketsOnlyInTheCityOfTheMunicipality {
	all m: Municipality | all tt: TrafficTicket | tt in m.trafficTicket implies tt.refersToReport.place.city=m.place.city
}

fact suggestionForMunicipalityWithSameCity {
	all s: Suggestion | s.place.city=s.forMunicipality.place.city
}

fact reportFromUserRefersToAParkingViolation {
	all r: ReportFromUser | one pv: ParkingViolationType | r.violation.violationType=pv
}

fact vehicleStatisticNotForUsers {
	all vS: VehicleStatistic | vS.forUser=none
}

fact trafficTicketsReferToReportInTheMunicipality {
	all tt: TrafficTicket | tt.refersToReport in ReportFromMunicipality or (one m: Municipality | tt in m.trafficTicket and tt.refersToReport in m.takenReportFromUser) 
}

fact eachPlaceInMunicipalityOrReport {
	all p: Place | (one m: Municipality | m.place=p) or (one r: Report | r.place=p)
}


//R7
fact theMunicipalityCanTakeReportedViolationOnlyOfItsCompetenceArea {
	all m: Municipality | all r: ReportFromUser | r in m.takenReportFromUser implies r.place.city=m.place.city
}


//R9
fact acceptValidReports {
	all r: ReportFromUser | one safeS: SafeStreets | r in safeS.reportedViolation
}


//R14
fact eachUserWasIdentified {
	all u: User | one s: Service | one idV: IdentityVerifier | s.serviceObject=u and
		s.thirdPart=idV
}


//R14
fact everyUserIsRegistered {
	all u: User | one r: UserRegistration | r.user=u
}


//R15
fact everyMunicipalityIsRegistered {
	all m: Municipality | one r: MunicipalityRegistration | r.municipality=m
}


--end of facts--



--Goals:--

//G1
pred acceptCompleteReports {
	#ReportFromUser>0
}

run acceptCompleteReports for 4

//G2
pred suggestInterventions {
	#Suggestion>0
}

run suggestInterventions for 2

//G3
assert theMunicipalityCanRetrieveSubmittedViolations {
	//After taken the violation, the Municipality has all and only the reported violations of its city
	all m: Municipality | theMunicipalityTakesTheReportedViolations[m, Violation] implies 
		(all r: ReportFromUser | r in m.takenReportFromUser implies r.place.city=m.place.city else r.place.city!=m.place.city)
}

check theMunicipalityCanRetrieveSubmittedViolations

//G4
pred giveStatisticsToTheUser {
	#Statistic>0
	some s: Statistic | s.forUser!=none
}

run giveStatisticsToTheUser

//G5
pred giveStatisticsToTheMunicipality {
	#Statistic>0
	some s: Statistic | s.forMunicipality!=none
}

run giveStatisticsToTheMunicipality

//G6
assert safeStreetsCanRetrieveViolationsFromTheMunicipality {
	all safeS: SafeStreets | all m: Municipality | safeStreetsRetrievesViolationsFromTheMunicipality[m, safeS] implies
		(all tt: TrafficTicket | tt in m.trafficTicket implies (tt.refersToReport in safeS.takenReportFromMunicipality or 
			tt.refersToReport in safeS.reportedViolation))
}

check safeStreetsCanRetrieveViolationsFromTheMunicipality

//G1 and G2 and G4 and G5
pred goals1and2and4and5 {
	#ReportFromUser>0
	#Suggestion>0
	#Statistic>0
	some s: Statistic | s.forUser!=none
	#Statistic>0
	some s: Statistic | s.forMunicipality!=none
}

run goals1and2and4and5 for 4

--end of goals--


--Operations:--

pred theMunicipalityTakesTheReportedViolations[m: Municipality, vs: set Violation] {
	m.takenReportFromUser=m.takenReportFromUser+vs
}


pred safeStreetsRetrievesViolationsFromTheMunicipality[m: Municipality, safeS: SafeStreets] {
	safeS.reportedViolation = safeS.reportedViolation+m.trafficTicket.refersToReport
}

--end of the operations--


//Worlds:

pred noMunicipality {
	#Municipality=0
}

pred oneMunicipality {
	#Municipality=1
}

pred moreMunicipalities {
	#Municipality=2
	#User=2
	#ReportFromUser=2
	#TrafficTicket=2
}


run moreMunicipalities for 12
