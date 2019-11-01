sig FiscalCode {}

abstract sig ServiceObject{} //it means that it can be an object of the service offered by a third part

sig User extends ServiceObject { //it can be verified by a third part
	fiscalCode: one FiscalCode
}

sig Password {}


sig Photo {}
sig Date {}
sig Time {}

sig Position extends ServiceObject {} //specific position in the world, identified by some coordinates
sig HouseCode{}
sig Street{}
sig City{}	
sig Place extends ServiceObject {
	city: one City,
	houseCode: one HouseCode,
	street: one Street,
	position: one Position
}

abstract sig ViolationType {}
sig ParkingViolationType extends ViolationType {} //violations reported by an user or verified by a Municipality
sig AccidentViolationType extends ViolationType {} //violations verified by a Municipality

sig LicensePlate extends ServiceObject {}
sig Vehicle {
	licensePlate: one LicensePlate
}

sig ViolationReport {
	user: one User,
	photo: some Photo,
	date: one Date,
	time: one Time,
	vehicle: one Vehicle,
	place: one Place
}

sig Violation {
	//if it is present it means that the violation was reported by an user
	//and that there is also the report of the user
	reportedByUser: lone User,
	violationReport: lone ViolationReport,
	photo: set Photo,
	date: one Date,
	time: one Time,
	vehicle: one Vehicle,
	place: one Place,
	violationType: one ViolationType
}

sig Municipality {
	place: one Place,
	trafficTicket: set TrafficTicket,
	takenReportedViolations: set Violation //reports about violations taken by the Municipality from Safestreets
}

sig UserRegistration {
	user: one User,
	password: one Password
}

sig MunicipalityRegistration {
	municipality: one Municipality,
	password: one Password
}

one sig SafeStreets {
	suggestion: set Suggestion,
	statistic: set Statistic,
	violation: set Violation
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
	refersToViolation: lone Violation
}

abstract sig Statistic {
	forUser: lone User, //means that the stastic is for an user
	forMunicipality: lone Municipality //means that the stastic is for an Municipality
}

sig streetStatistic extends Statistic{}
sig effectivenessOfServiceStatistic extends Statistic{}
sig vehicleStatistic extends Statistic{}


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


fact eachPhotoDateTimeVehicleToAReportOrViolation {
	all p: Photo | (one r: ViolationReport | p in r.photo) or (one v: Violation | p in v.photo)
	all d: Date | (one r: ViolationReport |  r.date=d) or (one v: Violation | v.date=d)
	all t: Time | (one r: ViolationReport |  r.time=t) or (one v: Violation | v.time=t)
	all ve: Vehicle | (one r: ViolationReport |  r.vehicle=ve) or (one v: Violation | v.vehicle=ve)
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


fact eachViolationNotFromMunicipalityRefersToAReport {
	all v: Violation | v.reportedByUser!=none implies 
		(one r: ViolationReport | v.violationReport=r and v.photo=r.photo and v.date=r.date and v.time=r.time and v.vehicle=r.vehicle and
			v.place=r.place and v.violationType in ParkingViolationType) else
		v.violationReport=none
}


fact eachViolationReportToViolation {
	all vR: ViolationReport | one v: Violation | v.violationReport=vR
}


fact eachTrafficTicketWasMadeByAMunicipality {
	all t: TrafficTicket | one m: Municipality | t in m.trafficTicket
}


fact eachPlaceAndPositionAndTrafficPlateInAReportWereIdentified {
	all p: Place | all vR: ViolationReport | p=vR.place implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all p: Position | all vR: ViolationReport | p=vR.place.position implies 
		one s: Service | one mS: MapService | s.thirdPart=mS and s.serviceObject=p

	all tP: LicensePlate | all vR: ViolationReport | tP=vR.vehicle.licensePlate implies 
		one s: Service | one rPS: RecognitionPlateSystem | s.thirdPart=rPS and s.serviceObject=tP
}

fact eachPasswordToAUser {
	all p: Password | (one r: UserRegistration | r.password=p) or (one r: MunicipalityRegistration | r.password=p)
}

fact eachStatisticAndSuggestionToSafeStreets {
	all stat: Statistic | one safeS: SafeStreets | stat in safeS.statistic
	all sugg: Suggestion | one safeS: SafeStreets | sugg in safeS.suggestion
}

fact eachViolationInSafeStreetsWasReportedByAnUserOrByAMunicipality {
	all v: Violation | all safeS: SafeStreets | v in safeS.violation implies v.reportedByUser!=none or 
		(some tt: TrafficTicket | tt.refersToViolation = v)
}

fact trafficTicketsOnlyInTheCityOfTheMunicipality {
	all m: Municipality | all tt: TrafficTicket | tt in m.trafficTicket implies tt.refersToViolation.place.city=m.place.city
}

fact suggestionForMunicipalityWithSameCity {
	all s: Suggestion | s.place.city=s.forMunicipality.place.city
}


//R7
fact theMunicipalityCanTakeReportedViolationOnlyOfItsCompetenceArea {
	all m: Municipality | all v: Violation | v in m.takenReportedViolations implies v.place.city=m.place.city and v.reportedByUser!=none
}


//R9
fact acceptValidReports {
	all vR:ViolationReport | one safeS: SafeStreets | some v:Violation | v.violationReport=vR and v in safeS.violation
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
	#ViolationReport>0
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
		(all v: Violation | v in m.takenReportedViolations implies v.place.city=m.place.city else v.place.city!=m.place.city)
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
		(all tt: TrafficTicket | tt in m.trafficTicket implies tt.refersToViolation in safeS.violation)
}

check safeStreetsCanRetrieveViolationsFromTheMunicipality

--end of goals--


--Operations:--

pred theMunicipalityTakesTheReportedViolations[m: Municipality, vs: set Violation] {
	m.takenReportedViolations=m.takenReportedViolations+vs
}


pred safeStreetsRetrievesViolationsFromTheMunicipality[m: Municipality, safeS: SafeStreets] {
	safeS.violation = safeS.violation+m.trafficTicket.refersToViolation
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
	#Place=3
	#LicensePlate>1
	#Street=3
	#Position=3
	#ViolationReport=4
	#TrafficTicket=2
	#AccidentViolationType=1
}


run moreMunicipalities for 12
