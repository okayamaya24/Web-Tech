<template>
    <div class="schedule">
        <table>
            <thead>
                <tr>
                    <th>Sport Type</th>
                    <th>Game Date and Time</th>
                    <th>Venue</th>
                    <th>Opponent (If Applicable)</th>
                    <th>Required Crew Positions</th>
                </tr>
            </thead>
            <tbody>
                 <!-- must have one game entry to be able to submit -->
                 <!-- need to work on how user can input information within a table -->
                 <tr> 
                    <td>
                        <input type="text">
                    </td>
                    <td>
                        <input type="datetime-local">
                    </td>
                    <td>
                        <input type="text">
                    </td>
                    <td>
                        <input type="text">
                    </td>
                    <td>
                        <input type="text">
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>


</script>

<style>
</style>