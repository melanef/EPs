class TheatersController < ApplicationController
  def index
    @theaters = Theater.all
  end

  def show
    @theater = Theater.find(params[:id])
    @theater_sessions = theater_sessions(@theater)
  end

  private
  def theater_sessions(theater)
    sessions = []

    rooms = Room.where(theater_id: theater.id)
    rooms.each do |room|
      room_sessions = Session.where(room_id: room.id)
      room_sessions.each do |session|
        sessions << session
      end
    end

    return sessions
  end
end
