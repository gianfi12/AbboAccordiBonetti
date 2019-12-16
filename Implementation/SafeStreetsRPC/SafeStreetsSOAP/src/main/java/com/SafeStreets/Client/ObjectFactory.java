
package com.SafeStreets.Client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.SafeStreets.Client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAvailableStatistics_QNAME = new QName("http://SafeStreets.com/", "getAvailableStatistics");
    private final static QName _AccessReportsResponse_QNAME = new QName("http://SafeStreets.com/", "accessReportsResponse");
    private final static QName _GetSuggestionsResponse_QNAME = new QName("http://SafeStreets.com/", "getSuggestionsResponse");
    private final static QName _NewReportResponse_QNAME = new QName("http://SafeStreets.com/", "newReportResponse");
    private final static QName _MunicipalityRegistrationResponse_QNAME = new QName("http://SafeStreets.com/", "municipalityRegistrationResponse");
    private final static QName _GetSuggestions_QNAME = new QName("http://SafeStreets.com/", "getSuggestions");
    private final static QName _Login_QNAME = new QName("http://SafeStreets.com/", "login");
    private final static QName _GetAvailableStatisticsResponse_QNAME = new QName("http://SafeStreets.com/", "getAvailableStatisticsResponse");
    private final static QName _RequestDataAnalysisResponse_QNAME = new QName("http://SafeStreets.com/", "requestDataAnalysisResponse");
    private final static QName _RequestDataAnalysis_QNAME = new QName("http://SafeStreets.com/", "requestDataAnalysis");
    private final static QName _AccessReports_QNAME = new QName("http://SafeStreets.com/", "accessReports");
    private final static QName _UserRegistration_QNAME = new QName("http://SafeStreets.com/", "userRegistration");
    private final static QName _MunicipalityRegistration_QNAME = new QName("http://SafeStreets.com/", "municipalityRegistration");
    private final static QName _NewReport_QNAME = new QName("http://SafeStreets.com/", "newReport");
    private final static QName _UserRegistrationResponse_QNAME = new QName("http://SafeStreets.com/", "userRegistrationResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://SafeStreets.com/", "loginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.SafeStreets.Client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MunicipalityRegistration }
     * 
     */
    public MunicipalityRegistration createMunicipalityRegistration() {
        return new MunicipalityRegistration();
    }

    /**
     * Create an instance of {@link NewReport }
     * 
     */
    public NewReport createNewReport() {
        return new NewReport();
    }

    /**
     * Create an instance of {@link UserRegistrationResponse }
     * 
     */
    public UserRegistrationResponse createUserRegistrationResponse() {
        return new UserRegistrationResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link AccessReports }
     * 
     */
    public AccessReports createAccessReports() {
        return new AccessReports();
    }

    /**
     * Create an instance of {@link UserRegistration }
     * 
     */
    public UserRegistration createUserRegistration() {
        return new UserRegistration();
    }

    /**
     * Create an instance of {@link RequestDataAnalysis }
     * 
     */
    public RequestDataAnalysis createRequestDataAnalysis() {
        return new RequestDataAnalysis();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetAvailableStatisticsResponse }
     * 
     */
    public GetAvailableStatisticsResponse createGetAvailableStatisticsResponse() {
        return new GetAvailableStatisticsResponse();
    }

    /**
     * Create an instance of {@link RequestDataAnalysisResponse }
     * 
     */
    public RequestDataAnalysisResponse createRequestDataAnalysisResponse() {
        return new RequestDataAnalysisResponse();
    }

    /**
     * Create an instance of {@link MunicipalityRegistrationResponse }
     * 
     */
    public MunicipalityRegistrationResponse createMunicipalityRegistrationResponse() {
        return new MunicipalityRegistrationResponse();
    }

    /**
     * Create an instance of {@link GetSuggestions }
     * 
     */
    public GetSuggestions createGetSuggestions() {
        return new GetSuggestions();
    }

    /**
     * Create an instance of {@link NewReportResponse }
     * 
     */
    public NewReportResponse createNewReportResponse() {
        return new NewReportResponse();
    }

    /**
     * Create an instance of {@link GetAvailableStatistics }
     * 
     */
    public GetAvailableStatistics createGetAvailableStatistics() {
        return new GetAvailableStatistics();
    }

    /**
     * Create an instance of {@link AccessReportsResponse }
     * 
     */
    public AccessReportsResponse createAccessReportsResponse() {
        return new AccessReportsResponse();
    }

    /**
     * Create an instance of {@link GetSuggestionsResponse }
     * 
     */
    public GetSuggestionsResponse createGetSuggestionsResponse() {
        return new GetSuggestionsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableStatistics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "getAvailableStatistics")
    public JAXBElement<GetAvailableStatistics> createGetAvailableStatistics(GetAvailableStatistics value) {
        return new JAXBElement<GetAvailableStatistics>(_GetAvailableStatistics_QNAME, GetAvailableStatistics.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessReportsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "accessReportsResponse")
    public JAXBElement<AccessReportsResponse> createAccessReportsResponse(AccessReportsResponse value) {
        return new JAXBElement<AccessReportsResponse>(_AccessReportsResponse_QNAME, AccessReportsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "getSuggestionsResponse")
    public JAXBElement<GetSuggestionsResponse> createGetSuggestionsResponse(GetSuggestionsResponse value) {
        return new JAXBElement<GetSuggestionsResponse>(_GetSuggestionsResponse_QNAME, GetSuggestionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "newReportResponse")
    public JAXBElement<NewReportResponse> createNewReportResponse(NewReportResponse value) {
        return new JAXBElement<NewReportResponse>(_NewReportResponse_QNAME, NewReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MunicipalityRegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "municipalityRegistrationResponse")
    public JAXBElement<MunicipalityRegistrationResponse> createMunicipalityRegistrationResponse(MunicipalityRegistrationResponse value) {
        return new JAXBElement<MunicipalityRegistrationResponse>(_MunicipalityRegistrationResponse_QNAME, MunicipalityRegistrationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSuggestions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "getSuggestions")
    public JAXBElement<GetSuggestions> createGetSuggestions(GetSuggestions value) {
        return new JAXBElement<GetSuggestions>(_GetSuggestions_QNAME, GetSuggestions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableStatisticsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "getAvailableStatisticsResponse")
    public JAXBElement<GetAvailableStatisticsResponse> createGetAvailableStatisticsResponse(GetAvailableStatisticsResponse value) {
        return new JAXBElement<GetAvailableStatisticsResponse>(_GetAvailableStatisticsResponse_QNAME, GetAvailableStatisticsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestDataAnalysisResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "requestDataAnalysisResponse")
    public JAXBElement<RequestDataAnalysisResponse> createRequestDataAnalysisResponse(RequestDataAnalysisResponse value) {
        return new JAXBElement<RequestDataAnalysisResponse>(_RequestDataAnalysisResponse_QNAME, RequestDataAnalysisResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestDataAnalysis }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "requestDataAnalysis")
    public JAXBElement<RequestDataAnalysis> createRequestDataAnalysis(RequestDataAnalysis value) {
        return new JAXBElement<RequestDataAnalysis>(_RequestDataAnalysis_QNAME, RequestDataAnalysis.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessReports }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "accessReports")
    public JAXBElement<AccessReports> createAccessReports(AccessReports value) {
        return new JAXBElement<AccessReports>(_AccessReports_QNAME, AccessReports.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "userRegistration")
    public JAXBElement<UserRegistration> createUserRegistration(UserRegistration value) {
        return new JAXBElement<UserRegistration>(_UserRegistration_QNAME, UserRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MunicipalityRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "municipalityRegistration")
    public JAXBElement<MunicipalityRegistration> createMunicipalityRegistration(MunicipalityRegistration value) {
        return new JAXBElement<MunicipalityRegistration>(_MunicipalityRegistration_QNAME, MunicipalityRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "newReport")
    public JAXBElement<NewReport> createNewReport(NewReport value) {
        return new JAXBElement<NewReport>(_NewReport_QNAME, NewReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "userRegistrationResponse")
    public JAXBElement<UserRegistrationResponse> createUserRegistrationResponse(UserRegistrationResponse value) {
        return new JAXBElement<UserRegistrationResponse>(_UserRegistrationResponse_QNAME, UserRegistrationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

}
