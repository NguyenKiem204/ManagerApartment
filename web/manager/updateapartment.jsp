<%-- 
    Document   : updateapartment
    Created on : Mar 6, 2025, 1:12:54 AM
    Author     : fptshop
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    /* Căn chỉnh form */
    #updateApartmentForm {
        background: #fff;
        padding: 20px;
        border-radius: 8px;
    }

    /* Căn chỉnh nhãn (label) */
    #updateApartmentForm label {
        font-weight: bold;
        margin-top: 10px;
        display: block;
    }

    /* Input và Select */
    #updateApartmentForm input,
    #updateApartmentForm select {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
    }

    /* Focus */
    #updateApartmentForm input:focus,
    #updateApartmentForm select:focus {
        border-color: #007bff;
        outline: none;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    }

    /* Nút Update */
    #saveUpdate {
        margin-top: 15px;
        width: 100%;
        background: #28a745;
        border: none;
        padding: 10px;
        font-size: 16px;
        border-radius: 5px;
        transition: 0.3s;
        color: white;
    }

    #saveUpdate:hover {
        background: #218838;
    }

</style>
<form id="updateApartmentForm">
    <input type="hidden" name="apartmentId" value="${apartment.apartmentId}">

    <label>Apartment Name:</label>
    <input type="text" name="apartmentName" value="${apartment.apartmentName}" class="form-control" required>

    <label>Block:</label>
    <select name="block" class="form-control">
        <option value="Block A" ${apartment.block == 'Block A' ? 'selected' : ''}>Block A</option>
        <option value="Block B" ${apartment.block == 'Block B' ? 'selected' : ''}>Block B</option>
        <option value="Block C" ${apartment.block == 'Block C' ? 'selected' : ''}>Block C</option>
    </select>

    <label>Status:</label>
    <select name="status" class="form-control">
        <option value="Available" ${apartment.status == 'Available' ? 'selected' : ''}>Available</option>
        <option value="Occupied" ${apartment.status == 'Occupied' ? 'selected' : ''}>Occupied</option>
        <option value="Maintenance" ${apartment.status == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
    </select>

    <label>Type:</label>
    <select name="type" class="form-control">
        <option value="1 Bedroom" ${apartment.type == '1 Bedroom' ? 'selected' : ''}>1 Bedroom</option>
        <option value="2 Bedrooms" ${apartment.type == '2 Bedrooms' ? 'selected' : ''}>2 Bedrooms</option>
        <option value="3 Bedrooms" ${apartment.type == '3 Bedrooms' ? 'selected' : ''}>3 Bedrooms</option>
    </select>

    <label>Owner ID:</label>
    <input type="number" name="ownerId" value="${apartment.ownerId}" class="form-control" required>

    <button type="button" class="btn btn-success" id="saveUpdate">Update</button>
</form>
