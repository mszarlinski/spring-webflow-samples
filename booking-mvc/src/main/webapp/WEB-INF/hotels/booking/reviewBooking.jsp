<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    function doConfirm() {
        var reviewBookingJSON = sessionStorage.getItem('BookingMvc.BookingData');
        var reviewBookingVM = JSON.parse(reviewBookingJSON);

        // copy form data to hidden input
        document.confirmationForm.reviewBookingData.value = JSON.stringify(reviewBookingVM.booking);
        document.confirmationForm.submit();
    }

    window.onload = function () {
        document.getElementById('confirmationForm').setAttribute('novalidate', ''); // disable browser validation
    };
</script>

<div class="container col-xs-10">
    <form:form id="confirmationForm" name="confirmationForm" modelAttribute="booking" action="${flowExecutionUrl}">

        <%@include file="reviewBooking.html" %>

        <input type="hidden" id="reviewBookingData" name="reviewBookingData">

        <div>
            <button class="btn btn-primary" type="submit" name="_eventId_CONFIRMED" onclick="doConfirm()">Confirm</button>
            <button class="btn btn-default" type="submit" name="_eventId_REVISED">Revise</button>
            <button class="btn btn-default" type="submit" name="_eventId_CANCELLED">Cancel</button>
        </div>
    </form:form>
</div>