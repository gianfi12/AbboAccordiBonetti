//open util/integer
//open util/boolean

sig FiscalCode {}

abstract sig Identified {}

sig IdentifiedYes extends Identified {}
sig IdentifiedNo extends Identified {}

sig User {
	fiscalCode: one FiscalCode,
	wasIdentified: one Identified
}

sig Photo {}
sig Date {}
sig Time {}
sig Position {}
sig TrafficPlate {}

sig ReportViolation {
	user: one User,
	photo: some Photo,
	date: one Date,
	time: one Time,
	position: one Position,
	trafficPlate: one TrafficPlate
}

sig Municipality {

}

sig SafeStreet {

}

sig Violation {

}

sig IdentityVerifier {
	verifiedUser: set User
} {
	#IdentityVerifier=1
}

fact everyUserWasIdentified {
	all u: User | u.wasIdentified in IdentifiedYes
	User=IdentityVerifier.verifiedUser
	
}

fact fiscalCodeIsUnique {
	all disj u1, u2: User | u1.fiscalCode != u2.fiscalCode
}

fact licencePlateIsUnique {

}


assert chainOfCustodyNeverBroken {
	
}

assert eachUserWasIdentified {

}


/*The report is complete if and only if:
the report was made by an identified user, there is the date and the time of the report,
there is at least one photo, there is the position of the violation and the 
traffic plate of the car that has commited the violation
*/
assert eachReportIsComplete {
	all r: ReportViolation | r.user.wasIdentified in IdentifiedYes
}

assert theUserCannotSeeSingleStoredViolations {

}

//Goals:
//G1
assert acceptCompleteReports {

}

//G2
assert suggestInterventions {

}

//G3
assert theMunicipalityCanRetrieveSubmittedViolations {

}

//G4
assert giveStatisticsToTheUser {

}

//G5
assert giveStatisticsToTheMunicipality {

}

//G6
assert safeStreetsCanRetrieveViolationsFromTheMunicipality {

}


pred noMunicipality {
	#Municipality=0
}

pred oneMunicipality {
	#Municipality=1
}

pred moreMunicipalities {
	#Municipality>1
}

run oneMunicipality for 10 but 1 ReportViolation

check eachReportIsComplete
