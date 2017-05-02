class Admin::SessionsController < Admin::ApplicationController
    def index

        if !params[:room_id].nil?
            @room = Room.find(params[:room_id])
            @sessions = @room.sessions
        elsif !params[:movie_id].nil?
            @movie = Movie.find(params[:movie_id])
            @sessions = @movie.sessions
        else
            @sessions = Session.all
        end

        @new_element_path = new_element_path params
    end

    def show
        @session = Session.find(params[:id])
    end

    def new
        @session = Session.new
        @room_collection = room_collection

        if !params[:room_id].nil?
            @session.room_id = params[:room_id]
        elsif !params[:movie_id].nil?
            @session.movie_id = params[:movie_id]
        end
    end

    def create
        @session = Session.new(session_params)
        if @session.save
            redirect_to admin_sessions_path
        else
            render 'new'
        end
    end

    def edit
        @session = Session.find(params[:id])
        @room_collection = room_collection
    end

    def update
        @session = Session.find(params[:id])

        if @session.update(session_params)
            redirect_to admin_sessions_path
        else
            render 'edit'
        end
    end

    def room_collection
        collection = []

        theaters = Theater.all
        theaters.each do |theater|
            rooms = Room.where(theater_id: theater.id)
            rooms.each do |room|
                collection << [ theater.name + " - " + room.name , room.id ]
            end
        end

        return collection
    end

    def new_element_path(params)
        path = new_admin_session_path

        if !params[:movie_id].nil?
            path = new_admin_movie_session_path(params[:movie_id])
        elsif !params[:theater_id].nil? && !params[:room_id].nil?
            path = new_admin_theater_room_session_path(params[:theater_id], params[:room_id])
        end

        return path
    end

    private
    def session_params
        params.require(:session).permit(:theater_id, :room_id, :movie_id, :datetime)
    end
end