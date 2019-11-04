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
	houseCode: lone HouseCode,
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

//Report made by a user
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

//Registration of a user
sig UserRegistration extends Registration {
	user: one User
}

//Registration of a Municipality
sig MunicipalityRegistration extends Registration {
	municipality: one Municipality
}

//SafeStreets' System
one sig SafeStreets {
	suggestion: set Suggestion,
	statistic: set Statistic,
	reportedViolation: set ReportFromUser,
	//Reports taken from the municipality
	takenReportFromMunicipality: set ReportFromMunicipality
}

//Third part that makes a service for SafeStreets
abstract sig ThirdPart{}
//Verifier of the identity of a user
one sig IdentityVerifier extends ThirdPart {}
//Recognizer of the traffic plate of a car
one sig RecognitionPlateSystem extends ThirdPart {}
//Third part that identifies the position and/or the street of a violation
one sig MapService extends ThirdPart {}

//Service made by a third part on an object
sig Service {
	serviceObject: one ServiceObject,
	thirdPart: one ThirdPart
}
//Traffic ticket
sig TrafficTicket {
	refersToReport: one Report
}
//Statistic
abstract sig Statistic {
	forUser: lone User, //means that the stastic is for a user
	forMunicipality: lone Municipality //means that the stastic is for an Municipality
} {
	//a statistic is always done for a user or for a Municipality
	#forUser=1 or #forMunicipality=1
}
//Statistic about the streets
sig StreetStatistic extends Statistic{}
//Statistic about the effectiveness of the service of SafeStreets
sig EffectivenessOfServiceStatistic extends Statistic{}
//Statistic about the vehicles
sig VehicleStatistic extends Statistic{}
//Statistic about the common violations
sig CommonViolationStatistic extends Statistic{}

//Suggestion for the Municipality
sig Suggestion {
	//Place where the suggestion refers
	place: one Place,
	forMunicipality: one Municipality
}


--Facts:--
//Each user has a unique fiscal code
fact eachUserHasAnUniqueFiscalCode {
	all disj u1, u2 : User | u1.fiscalCode != u2.fiscalCode
}

//Each fiscal code is associated to a user
fact eachFiscalCodeToAnUser {
	all f: FiscalCode | one u: User | u.fiscalCode=f
}

//Each Municipality resides on a different city
fact eachMunicipalityIsInADifferentCity {
	all disj m1, m2: Municipality | m1.place.city != m2.place.city
}

//Each place has a different position
fact eachPlaceHasDifferentPosition {
	all disj p1, p2: Place | p1.position!=p2.position and (p1.city!=p2.city or p1.houseCode!=p2.houseCode or
		p1.street!=p2.street)
}

//Each vehicle has a unique license plate
fact licencePlateIsUnique {
	all disj v1, v2: Vehicle | v1.licensePlate!=v2.licensePlate
}

//Each license plate is associated to a vehicle
fact eachLicencePlateToAVehicle {
	all l: LicensePlate | one v: Vehicle | v.licensePlate=l
}

//Each traffic ticket was made by one and only one Municipality
fact trafficTicketMadeByOneMunicipality {
	all disj m1, m2: Municipality | m1.trafficTicket & m2.trafficTicket = none
	
	all t: TrafficTicket | one m: Municipality | t in m.trafficTicket
}

//Each photo, timestamp and vehicle are associated to a report
fact eachPhotoTimeVehicleToAReport {
	all p: Photo | (one r: ReportFromUser | p in r.photo)
	all t: Timestamp | (one r: Report |  r.timeOfWatchedViolation=t or r.timeOfReport=t)
	all ve: Vehicle | (one r: Report |  r.vehicle=ve)
}

//Each violation type is associated to a violation
fact eachViolationTypeToAViolation {
	all vT: ViolationType | one v: Violation | v.violationType=vT
}

//Each city, house code, street and position are associated to a place
fact eachCityHouseCodeStreetCityPositionToAPlace {
	all c: City | one p : Place | p.city=c	
	all hC: HouseCode | one p : Place | p.houseCode=hC
	all s: Street | one p : Place | p.street=s
	all pos: Position | one p : Place | p.position=pos
}

//Each place, position and license plate in a report from a user were identified by a third part
fact eachPlaceAndPositionAndTrafficPlateInAReportWereIdentified {
	all p: Place | all vR: ReportFromUser | p=vR.place implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all p: Position | all vR: ReportFromUser | p=vR.place.position implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all tP: LicensePlate | all vR: ReportFromUser | tP=vR.vehicle.licensePlate implies 
		one s: Service | one rPS: RecognitionPlateSystem | s.thirdPart=rPS and s.serviceObject=tP
}

//Each place and position can verified by only the MapService
fact eachPlaceAndPositionIdentifiedByOnlyMapService {
	all p: Place | all t: ThirdPart | not( some s: Service | s.thirdPart=t and s.serviceObject=p and t not in MapService)

	all p: Position | all t: ThirdPart | not( some s: Service | s.thirdPart=t and s.serviceObject=p and t not in MapService)
}

//Each place and position can verified by only the MapService
fact eachLicensePlateIdentifiedByOnlyRecognitionPlateSystem {
	all lp: LicensePlate | all t: ThirdPart | not( some s: Service | s.thirdPart=t and s.serviceObject=lp and t not in RecognitionPlateSystem)
}

//Each Password is associated to a registration
fact eachPasswordToARegistration {
	all p: Password | one r: Registration | r.password=p
}

//Each statistic and suggestion were made by SafeStreets
fact eachStatisticAndSuggestionToSafeStreets {
	all stat: Statistic | one safeS: SafeStreets | stat in safeS.statistic
	all sugg: Suggestion | one safeS: SafeStreets | sugg in safeS.suggestion
}

//Each traffic ticket refers to a violation made in the city of the Municipality that has done the traffic ticket
fact trafficTicketsOnlyInTheCityOfTheMunicipality {
	all m: Municipality | all tt: TrafficTicket | tt in m.trafficTicket implies tt.refersToReport.place.city=m.place.city
}

//Each suggestion is for a Municipality that resides on the same city of the place of the suggestion
fact suggestionForMunicipalityWithSameCity {
	all s: Suggestion | s.place.city=s.forMunicipality.place.city
}

//All reports from a user refers to a parking violation
fact reportFromUserRefersToAParkingViolation {
	all r: ReportFromUser | one pv: ParkingViolationType | r.violation.violationType=pv
}

//The statistics about the vehicles are not for the users
fact vehicleStatisticNotForUsers {
	all vS: VehicleStatistic | vS.forUser=none
}

//Each traffic ticket refers to a report present in the Municipality that has done the traffic ticket
fact trafficTicketsReferToReportInTheMunicipality {
	all tt: TrafficTicket | tt.refersToReport in ReportFromMunicipality or (one m: Municipality | tt in m.trafficTicket 
		and tt.refersToReport in m.takenReportFromUser) 
}

//Each place is associated to a Municipality or a report
fact eachPlaceInMunicipalityOrReport {
	all p: Place | (one m: Municipality | m.place=p) or (one r: Report | r.place=p)
}

//For a statistic there must be at least one report from a user
//and for a suggestiont there must be at least one report from a user in the same place of the suggestion
fact forEachStatisticAndSuggestionThereMustBeAtLeastOneReportFromUser {
	all s: Statistic | some r: ReportFromUser | one safeS: SafeStreets | r in safeS.reportedViolation and s in
		safeS.statistic
	all s: Suggestion | some r: ReportFromUser | one safeS: SafeStreets | r in safeS.reportedViolation and 
		s.place in r.place
}


//R7
//The Municipality can take the reports from SafeStreets only if its competence area (its city)
fact theMunicipalityCanTakeReportedViolationOnlyOfItsCompetenceArea {
	all m: Municipality | all r: ReportFromUser | r in m.takenReportFromUser implies r.place.city=m.place.city
}


//R9
//Accept valide reports
//In SafeStreets there are all the reports from the users
fact acceptValidReports {
	all r: ReportFromUser | one safeS: SafeStreets | r in safeS.reportedViolation
}


//R14
//Each user was identified by an Identity Verifier
fact eachUserWasIdentified {
	all u: User | one s: Service | one idV: IdentityVerifier | s.serviceObject=u and
		s.thirdPart=idV
}


//R14
//Every user is registered
fact everyUserIsRegistered {
	all u: User | one r: UserRegistration | r.user=u
}


//R15
//Every Municipality is registered
fact everyMunicipalityIsRegistered {
	all m: Municipality | one r: MunicipalityRegistration | r.municipality=m
}


--end of facts--



--Goals:--

//G1
//The System accepts valid reports by the users about the parking violations
//There is at least one report from a user
pred acceptCompleteReports {
	#ReportFromUser>0
}

run acceptCompleteReports for 4

//G2
//The System suggests possible interventions to the Municipality
//There is at least one suggestion from SafeStreets
pred suggestInterventions {
	#Suggestion>0
}

run suggestInterventions for 4

//G3
//The System allows the Municipality to retrieve submitted parking violations of its competence area
//After taken the violation, the Municipality has all and only the reported violations of its city
assert theMunicipalityCanRetrieveSubmittedViolations {
	all m: Municipality | theMunicipalityTakesTheReportedViolations[m, Violation] implies 
		(all r: ReportFromUser | r in m.takenReportFromUser implies r.place.city=m.place.city else r.place.city!=m.place.city)
}

check theMunicipalityCanRetrieveSubmittedViolations

//G4
//The System gives some statistics to the User about the violations
//There is at least statistic from SafeStreets for a user
pred giveStatisticsToTheUser {
	#Statistic>0
	some s: Statistic | s.forUser!=none
}

run giveStatisticsToTheUser for 4

//G5
//The System can give all the statistics to the Municipality about the violations.
//There is at least statistic from SafeStreets for a Municipality
pred giveStatisticsToTheMunicipality {
	#Statistic>0
	some s: Statistic | s.forMunicipality!=none
}

run giveStatisticsToTheMunicipality for 4

//G6
//The System can retrieve the violations verified by the Municipality
assert safeStreetsCanRetrieveViolationsFromTheMunicipality {
	all safeS: SafeStreets | all m: Municipality | safeStreetsRetrievesViolationsFromTheMunicipality[m, safeS] implies
		(all tt: TrafficTicket | tt in m.trafficTicket implies (tt.refersToReport in safeS.takenReportFromMunicipality or 
			tt.refersToReport in safeS.reportedViolation))
}

check safeStreetsCanRetrieveViolationsFromTheMunicipality

//G1 and G2 and G4 and G5
//G1
//The System accepts valid reports by the users about the parking violations
//There is at least one report from a user
//G2
//The System suggests possible interventions to the Municipality
//There is at least one suggestion from SafeStreets
//G4
//The System gives some statistics to the User about the violations
//There is at least statistic from SafeStreets for a user
//G5
//The System can give all the statistics to the Municipality about the violations.
//There is at least statistic from SafeStreets for a Municipality
pred goals1and2and4and5 {
	acceptCompleteReports
	suggestInterventions
	giveStatisticsToTheUser
	giveStatisticsToTheMunicipality
}

run goals1and2and4and5 for 4

--end of goals--


--Operations:--
//The Municipality takes the reports from the users
pred theMunicipalityTakesTheReportedViolations[m: Municipality, vs: set Violation] {
	m.takenReportFromUser=m.takenReportFromUser+vs
}

//SafeStreets retrives the reports from a Municipality
pred safeStreetsRetrievesViolationsFromTheMunicipality[m: Municipality, safeS: SafeStreets] {
	safeS.reportedViolation = safeS.reportedViolation+m.trafficTicket.refersToReport
}

--end of the operations--


//Worlds:
//Without Municipalities
pred noMunicipality {
	#Municipality=0
	#ReportFromUser=1
}

run noMunicipality for 4

//With only one Municipality
pred oneMunicipality {
	#Municipality=1
}

run oneMunicipality

//With two Municipalities
pred moreMunicipalities {
	#Municipality=2
	#User=2
	#ReportFromUser=2
	#TrafficTicket=2
}

run moreMunicipalities for 12
