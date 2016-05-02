<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    function doConfirm() {
        var reviewBookingJSON = sessionStorage.getItem('BookingMvc.ReviewBooking');
        var reviewBookingVM = JSON.parse(reviewBookingJSON);
        document.confirm.reviewBookingData.value = JSON.stringify(reviewBookingVM.booking);
        document.confirm.submit();
    }
</script>

<div class="container col-xs-10">
    <form:form id="confirm" name="confirm" modelAttribute="booking" action="${flowExecutionUrl}">

        <%@include file="reviewBooking.html" %>

        <input type="hidden" id="reviewBookingData" name="reviewBookingData">

        <div>
            <button class="btn btn-primary" type="submit" name="_eventId_confirm" onclick="doConfirm()">Confirm</button>
            <button class="btn btn-default" type="submit" name="_eventId_revise">Revise</button>
            <button class="btn btn-default" type="submit" name="_eventId_cancel">Cancel</button>
        </div>
    </form:form>
</div>